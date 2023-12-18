package decision.service;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import decision.bo.DslAgent;
import decision.constant.DSLConstant;
import decision.util.PathUtils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.logging.Slf4j;
import org.assertj.core.util.Lists;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
@Slf4j
@SuppressWarnings("UnstableApiUsage")
public class GroovyScriptRunner {
    private static final Map<Long, Script> SCRIPT_MAP = new ConcurrentHashMap<>();
    public static final ThreadLocal<Binding> bindingThreadLocal = new ThreadLocal<>();
    public static String name = "GroovyScriptRunner";

    public static Object getBean(String beanName){
        if ("GroovyScriptRunner".equalsIgnoreCase(beanName)) {
            return new GroovyScriptRunner();
        }
        return null;
    }

    public static String getScriptContent(String module, String api, Map<String, Object> params) {
//        strategyInt1
        return getScriptContent(module, api, "dsl", params);
    }

    public static String getScriptContent(String module, String api, String rootDir, Map<String, Object> params) {

        //简单的通过api是否包含"."来判断其是否是全文件名
        String apiFullName = api.split("\\.").length > 1 ? api : api + ".groovy";

        String path = rootDir + "/" + module + "/" + apiFullName;
        return getScriptContentByPath(path);
    }
    private static String getScriptContentByPath(String path) {

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(DslAgent.class.getName());
        GroovyShell shell = new GroovyShell(GroovyScriptRunner.class.getClassLoader());
        try {
            URL classpathUrl = GroovyScriptRunner.class.getClassLoader().getResource(path);
            String filePath = PathUtils.parseFilePath(classpathUrl);
            File file = new File(filePath);
            Charset charset = Charset.forName(compilerConfiguration.getSourceEncoding());
            List<String> lines = Files.readLines(file, charset);
            String content = String.join("\n", lines);
            return content;
        } catch (IOException e) {
            throw new RuntimeException("getScript error,path: " + path, e);
        }
    }

    public static Script getScript(String module, String api, Map<String, Object> params) {
        return getScript(module, api, "dsl", params);
    }

    public static Script getScript(String module, String api, String rootDir, Map<String, Object> params) {
        //简单的通过api是否包含"."来判断其是否是全文件名
        String apiFullName = api.split("\\.").length > 1 ? api : api + ".groovy";

        String path = rootDir + "/" + module + "/" + apiFullName;
        Script base = getScriptByPath(path);
        Binding binding = new Binding();
        binding.setProperty(DSLConstant.MODULE, module);
        binding.setProperty(DSLConstant.API, api);
        params.forEach(binding::setVariable);

        bindingThreadLocal.set(binding);
        return InvokerHelper.createScript(base.getClass(), binding);
    }

    private static Script getScriptByPath(String path) {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(DslAgent.class.getName());
        GroovyShell shell = new GroovyShell(GroovyScriptRunner.class.getClassLoader());
        try {
            URL classpathUrl = GroovyScriptRunner.class.getClassLoader().getResource(path);
            String filePath = PathUtils.parseFilePath(classpathUrl);
            File file = new File(filePath);
            Charset charset = Charset.forName(compilerConfiguration.getSourceEncoding());
            List<String> lines = Files.readLines(file, charset);
            String content = String.join("", lines);
            final long hashing = Hashing.goodFastHash(128).hashBytes(content.getBytes()).asLong();
            if (SCRIPT_MAP.containsKey(hashing)) {
                return SCRIPT_MAP.get(hashing);
            }
            Script script = shell.parse(file);
            SCRIPT_MAP.put(hashing, script);
            return script;
        } catch (IOException e) {
            throw new RuntimeException("getScript error,path: " + path, e);
        }
    }

    public static ScriptContentDetail deCode(String scriptContent) {
        if (StringUtils.isBlank(scriptContent)) {
            return null;
        }
        ScriptContentDetail ret = new ScriptContentDetail();
        StringBuilder sb = new StringBuilder();

        List<String> listContents = Arrays.asList(scriptContent.split("\n"));
        listContents.stream().map(content -> content.trim())
                .collect(Collectors.toList())
                .stream()
                .forEach(scriptLine -> {
                    if (scriptLine.startsWith("import")) {
                        ret.imports.add(scriptLine.substring(scriptLine.indexOf("import")+6, scriptLine.length()).trim());
                    }else {
                        sb.append(scriptLine);
                        sb.append("\n");
                    }
                });
        ret.dslContent = sb.toString();
        return ret;
    }

    public static class ScriptContentDetail {
        public String dslContent;
        public List<String> imports;
        public ScriptContentDetail(){
            imports = Lists.newArrayList();
            dslContent = "";
        }
    }

}
