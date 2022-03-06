package jackdking.groovy.controller;

import jackdking.groovy.infrastruture.command.GroovyBeanCommand;
import jackdking.groovy.infrastruture.util.GroovyContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GroovyScriptController
 * @Description TODO
 * @Author jackdking
 * @Date 12/01/2022 12:02 下午
 * @Version 2.0
 **/
@Controller
public class GroovyScriptController {

    @PostMapping("/command/add")
    public String addGroovyCommand(@RequestParam String groovyBeanName, @RequestParam String script) {
        GroovyContextUtils.autowireBean(groovyBeanName, script);
        return "SUCCESS";
    }

    @GetMapping("/command/run")
    public String runGroovyCommand(@RequestParam String groovyBeanName) {
        GroovyBeanCommand command = GroovyContextUtils.getBean(groovyBeanName, GroovyBeanCommand.class);
        return command.run();
    }
}