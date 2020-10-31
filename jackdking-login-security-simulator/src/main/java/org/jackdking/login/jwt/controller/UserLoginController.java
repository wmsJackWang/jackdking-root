package org.jackdking.login.jwt.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jackdking.login.jwt.anoation.PreAuthority;
import org.jackdking.login.jwt.domain.UserDetail;
import org.jackdking.login.jwt.inteceptor.JdkApiInterceptor;
import org.jackdking.login.jwt.utils.CookieUtil;
import org.jackdking.login.jwt.utils.RedisOperator;
import org.jackdking.login.jwt.utils.RestResponseBo;
import org.jackdking.login.jwt.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Controller
public class UserLoginController {
	
    /**
     * redis token超时时间（ms）30分钟
     */
    public static final int REDIS_TIMEOUT = 1000 * 60 * 30;
	
	@Autowired
	RedisOperator operator;
	
    @GetMapping(value = {"/login","/"})
    public String login() {
        return "login";
    }
    
	
    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }
    
    
    @PostMapping(value = "loginCheck")
	@ResponseBody
    public RestResponseBo loginCheck(@RequestParam String username,
            		@RequestParam String password,
            		HttpServletRequest request,
            		HttpServletResponse response) {
    	  if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){

              return RestResponseBo.fail("用户名或者密码不能为空!");
          }
    	  if(!(username.equals("admin")&&password.equals("admin"))&&!(username.equals("user")&&password.equals("user")))
    	  {

              return RestResponseBo.fail("用户名或者密码不正确!");
    	  }
    	  
    	  String token = StrUtil.uuid();

          //存放唯一的 token 并设置过期时间
    	  operator.set(JdkApiInterceptor.USER_REDIS_SESSION + ":" + username, token, REDIS_TIMEOUT);
    	  
    	  //设置用户  密码  token等信息 
          operator.set(username, username+":"+password+":"+token);
          
          if(username.equals("admin")) {
              UserDetail userDetail = new UserDetail();
              userDetail.setUsername(username);
              List<String> roles = new ArrayList<String>();
              roles.add("admin");
              roles.add("user");
              userDetail.setRoles(roles);
              operator.set(JdkApiInterceptor.USER_REDIS_DETAIL + ":" + username, JSONUtil.toJsonStr(userDetail));
          }
          
          if(username.equals("user")){
              UserDetail userDetail = new UserDetail();
              userDetail.setUsername(username);
              List<String> roles = new ArrayList<String>();
              roles.add("user");
              userDetail.setRoles(roles);
              operator.set(JdkApiInterceptor.USER_REDIS_DETAIL + ":" + username, JSONUtil.toJsonStr(userDetail));
          }

          //用户浏览器会存放两种cookie: userToken,userId。
          CookieUtil.addCookie("userName", username);

          return RestResponseBo.ok();//
		}
    
    
    
    @PostMapping(value = {"/admin"})
    @PreAuthority(roles= "admin")
	@ResponseBody
    public RestResponseBo admin() {
    	RestResponseBo<String> result = new RestResponseBo<>(true); 
    	result.setPayload("admin 才能访问的信息");
        return result;
    }
    
    @PostMapping(value = {"/user"})
    @PreAuthority(roles= "user")
	@ResponseBody
    public RestResponseBo user() {
    	RestResponseBo<String> result = new RestResponseBo<>(true); 
    	result.setPayload("user 访问的信息");
        return result;
    }
}
