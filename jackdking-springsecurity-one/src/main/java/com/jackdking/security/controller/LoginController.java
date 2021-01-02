package com.jackdking.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jackdking.security.pojo.GalenResponse;
import com.jackdking.security.pojo.RespBean;
import com.jackdking.security.utils.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by sang on 2017/12/29.
 */
@Api(value = "登录", tags = "登录，注销等操作")
@Controller
public class LoginController {

//    @ApiOperation(value = "swagger端测试登录入口")
//    @PostMapping("/login")
//    @ResponseBody
//    public RespBean login(String username, String password) {
//        System.out.println(username + "------" + password);
//        return RespBean.ok("【LoginController.login】登录成功!");
//    }

    @ApiOperation(value = "swagger端测试注销入口")
    @PostMapping("/logout")
    @ResponseBody
    public GalenResponse logout() {
        System.out.println("注销成功!");
        return ResponseUtils.SUCCESS("注销成功!");
    }

    @ApiOperation(value = "提示跳转到登录页面")
    @GetMapping("/login_p")
    public String loginP() {
//        return ResponseUtils.invalid();
    	return "login";
    }
    
    @ApiOperation(value = "提示跳转到登录页面")
    @GetMapping("/index")
    public String index(Model model) {
//        return ResponseUtils.invalid();
//    	System.out.println("index");
    	Map msg = new HashMap<String, String>();
    	msg.put("title", "主页面");
    	msg.put("content", "用户权限显示");
    	msg.put("etraInfo", "此用户是admin权限用户");
    	model.addAttribute("msg",msg);
    	
    	return "index";
    }

    
    @ApiOperation(value = "跳转到自定义错误页面，如果是error 则自动覆盖了springboot默认的error页面")
    @GetMapping("/error_p")
    public String error(HttpServletRequest request , HttpServletResponse response , Model model) {
//        return ResponseUtils.invalid();
    	
    	RespBean respBean = (RespBean)request.getAttribute("respBean");
    	model.addAttribute("respBean",respBean);
    	System.out.println(respBean.toString());
    	return "error_p";
    }

}
