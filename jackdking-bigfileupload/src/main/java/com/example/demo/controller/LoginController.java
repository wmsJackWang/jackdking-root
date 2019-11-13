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
}
