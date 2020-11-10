package com.jackdking.security.interceptor.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jackdking.security.pojo.RespBean;
import com.jackdking.security.utils.SecurityUserUtil;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证成功的处理
 **/
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = RespBean.ok("【MyAuthenticationSuccessHandler】登录成功!", SecurityUserUtil.getCurrentUser());
        new GalenWebMvcWrite().writeToWeb(request,response, respBean);
        System.out.println("【MyAuthenticationSuccessHandler】登录成功!");
    }
}
