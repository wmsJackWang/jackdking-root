package parser;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * 脚本执行工具类
 *
 * @author zrz
 * @date 2021/5/13 14:06
 */
public class ScriptUtil {

    ScriptUtil(){}
    /**
     * 脚本语言groovy
     */
    public static final String SCRIPT_GROOVY = "groovy";

    private static final ScriptEngineManager ENGINE_MANAGER = new ScriptEngineManager(){};
    /**
     * 脚本引擎缓存
     */
    private static final Map<String, ScriptEngine> SCRIPT_ENGINE_MAP = new HashMap<>();
    static {
        SCRIPT_ENGINE_MAP.put(SCRIPT_GROOVY, ENGINE_MANAGER.getEngineByName(SCRIPT_GROOVY));
    }

    /**
     * 执行脚本中的方法
     *
     * @param scriptLang 脚本语言
     * @param script 需要执行的脚本文本
     * @param args 执行脚本方法传入的参数
     * @param functionName 执行的方法名
     * @return 返回执行结果
     * @throws ScriptException 脚本异常
     * @throws NoSuchMethodException 找不到定义的函数
     */
    public static Object invokeScriptFunction(String scriptLang, String script, String functionName, Object... args)
            throws ScriptException, NoSuchMethodException {

        ScriptEngine engine = SCRIPT_ENGINE_MAP.get(scriptLang);
        engine.eval(script);
        return ((Invocable) engine).invokeFunction(functionName, args);
    }
}