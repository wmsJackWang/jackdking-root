package com.springsecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class UserFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest myrequesst = (HttpServletRequest)request;
        System.out.println("UserFilter 在 user 过滤链的  UsernamePasswordAuthenticationFilter 前调用 , 过滤器拦截的url是："+myrequesst.getRequestURI());
        chain.doFilter(request, response);
    }
}

server {
  listen 443;
  server_name exam.bittechblog.com; 
  ssl on;
  ssl_certificate  /usr/local/nginx/cert/exam.bittechblog.com.key;
  ssl_certificate_key /usr/local/nginx/cert/exam.bittechblog.com.pem;
  location / {
   proxy_pass  localhost:8082;
  }
  
  
  server {
	　　listen 443 ssl;
	　　server_name exam.bittechblog.com; 
	　　ssl on;
	　　ssl_certificate /usr/local/nginx/cert/exam.bittechblog.com.key;
	　　ssl_certificate_key /usr/local/nginx/cert/exam.bittechblog.com.pem;

	　　ssl_session_cache shared:SSL:1m;
	　　ssl_session_timeout 10m;

	　　ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!aNULL:!MD5:!ADH:!RC4;
	　　ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
	　　ssl_prefer_server_ciphers on;

	　　location / {
	　　　　proxy_pass localhost:8082;
	　　}
	}