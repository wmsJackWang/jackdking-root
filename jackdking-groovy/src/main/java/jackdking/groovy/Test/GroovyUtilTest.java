//package jackdking.groovy.Test;
//
//import groovy.util.logging.Slf4j;
//import jackdking.groovy.infrastruture.commands.GroovyBeanCommand;
//import jackdking.groovy.infrastruture.util.GroovyContextUtils;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * Copyright (C) 阿里巴巴
// *
// * @ClassName GroovyUtilTest
// * @Description TODO
// * @Author jackdking
// * @Date 12/01/2022 2:18 下午
// * @Version 2.0
// **/
//@Component
//@Slf4j
//public class GroovyUtilTest implements ApplicationRunner {
//
//    String script = "import jackdking.groovy.bean.HelloWorld; import jackdking.groovy.infrastruture.command.GroovyBeanCommand; import org.springframework.beans.factory.annotation.Autowired;  class SampleGroovyCommand implements GroovyBeanCommand{  @Autowired  HelloWorld helloWorld;     @Override String run() { println('hello world');  helloWorld.getWords(); " +
//            " return 'hello world';}  }";
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        GroovyContextUtils.autowireBean("test", script);
//
//        GroovyBeanCommand groovyBeanCommand = GroovyContextUtils.getBean("test", GroovyBeanCommand.class);
//
//        groovyBeanCommand.run();
//    }
//}
