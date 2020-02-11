package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by e-shenbin1 on 2018/11/1.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String login() {
        return "demo";
    }
    
    @RequestMapping(value = "/b" , method = RequestMethod.GET)
    public String bootstrap() {
        return "demoq";
    }
    @RequestMapping(value = "/tab_first" , method = RequestMethod.GET)
    public String first() {
        return "tab_first";
    }
    @RequestMapping(value = "/tab_second" , method = RequestMethod.GET)
    public String second() {
        return "tab_second";
    }
    @RequestMapping(value = "/tab_third" , method = RequestMethod.GET)
    public String third() {
        return "tab_third";
    }
    @RequestMapping(value = "/tab_content" , method = RequestMethod.GET)
    public String content() {
        return "tab_content";
    }
}
