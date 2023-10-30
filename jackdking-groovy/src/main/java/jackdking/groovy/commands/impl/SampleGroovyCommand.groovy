package jackdking.groovy.commands.impl

import jackdking.groovy.bean.HelloWorld
import jackdking.groovy.commands.GroovyBeanCommand
import org.springframework.beans.factory.annotation.Autowired

class SampleGroovyCommand implements GroovyBeanCommand{

    @Autowired
    HelloWorld helloWorld;

    @Override
    String run() {

        println("hello world");
        helloWorld.getWords();

        return "hello world";
    }
}
