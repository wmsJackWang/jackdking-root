package jackdking.groovy.Test;

import groovy.util.logging.Slf4j;
import jackdking.groovy.commands.GroovyBeanCommand;
import jackdking.groovy.infrastruture.util.GroovyContextUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GroovyUtilTest
 * @Description TODO
 * @Author jackdking
 * @Date 12/01/2022 2:18 下午
 * @Version 2.0
 **/
@Component
@Slf4j
public class GroovyUtilTest implements ApplicationRunner {

    String script = "package jackdking.groovy.commands.impl\n" +
      "\n" +
      "import jackdking.groovy.bean.HelloWorld\n" +
      "import jackdking.groovy.commands.GroovyBeanCommand\n" +
      "import org.springframework.beans.factory.annotation.Autowired\n" +
      "\n" +
      "class SampleGroovyCommand implements GroovyBeanCommand{\n" +
      "\n" +
      "    @Autowired\n" +
      "    HelloWorld helloWorld;\n" +
      "\n" +
      "    @Override\n" +
      "    String run() {\n" +
      "\n" +
      "        println(\"hello world\");\n" +
      "        helloWorld.getWords();\n" +
      "\n" +
      "        return \"hello world\";\n" +
      "    }\n" +
      "}\n";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GroovyContextUtils.autowireBean("test", script);

        GroovyBeanCommand groovyBeanCommand = GroovyContextUtils.getBean("test", GroovyBeanCommand.class);

        groovyBeanCommand.run();
    }
}
