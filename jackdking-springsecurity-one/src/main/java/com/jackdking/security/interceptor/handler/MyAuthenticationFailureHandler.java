package com.jackdking.security.interceptor.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.jackdking.security.pojo.RespBean;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证失败的处理
 **/
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        RespBean respBean;
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            respBean = RespBean.error("账户名或者密码输入错误!");
        } else if (exception instanceof LockedException) {
            respBean = RespBean.error("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            respBean = RespBean.error("密码过期，请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            respBean = RespBean.error("账户过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            respBean = RespBean.error("账户被禁用，请联系管理员!");
        } else {
            respBean = RespBean.error("登录失败!");
        }
        //response.setStatus(401);
        new GalenWebMvcWrite().writeToWeb(response, respBean);
    }
}
