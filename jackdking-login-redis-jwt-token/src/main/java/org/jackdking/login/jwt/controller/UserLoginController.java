package org.jackdking.login.jwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jackdking.login.jwt.inteceptor.JdkApiInterceptor;
import org.jackdking.login.jwt.utils.CookieUtil;
import org.jackdking.login.jwt.utils.JwtTokenProvider;
import org.jackdking.login.jwt.utils.RedisOperator;
import org.jackdking.login.jwt.utils.RestResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.core.util.StrUtil;

@Controller
public class UserLoginController {
	
    /**
     * redis token超时时间（ms）
     */
    public static final int REDIS_TIMEOUT = 1000 * 60 * 30;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
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
    	  if(!username.equals("admin") || !password.equals("admin"))
    	  {

              return RestResponseBo.fail("用户名或者密码不正确!");
    	  }
    	  
    	  String jwtToken = jwtTokenProvider.createToken(username);

          //用户浏览器会存放两种cookie: userToken,userId。
          jwtTokenProvider.saveJwtToken(jwtToken);

          return RestResponseBo.ok();
		}
    
    
    @PostMapping(value = "loginOut")
	@ResponseBody
    public RestResponseBo loginOut() {
    	
    	
    	jwtTokenProvider.removeJwtToken();
    	

        return RestResponseBo.ok();
    	
    }
    
    
    
}
