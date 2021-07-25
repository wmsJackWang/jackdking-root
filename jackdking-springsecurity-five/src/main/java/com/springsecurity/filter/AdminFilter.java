package com.springsecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class AdminFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest myrequesst = (HttpServletRequest)request;
        System.out.println("AdminFilter在 admin 过滤链的 UsernamePasswordAuthenticationFilter 前调用 , 过滤器拦截的url是："+myrequesst.getRequestURI());
        chain.doFilter(request, response);
    }
}


