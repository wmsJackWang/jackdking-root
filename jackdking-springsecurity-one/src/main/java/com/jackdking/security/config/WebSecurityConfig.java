package com.jackdking.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

import com.jackdking.security.interceptor.handler.MyAuthenticationFailureHandler;
import com.jackdking.security.interceptor.handler.MyAuthenticationSuccessHandler;
import com.jackdking.security.service.impl.UserSecurityService;

/**
 * @Author: Galen
 * @Date: 2019/3/27-14:43
 * @Description: spring-security权限管理的核心配置
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSecurityService userSecurityService;

    /**
     * @Author: Galen
     * @Description: 配置userDetails的数据源，密码加密格式
     * @Date: 2019/3/28-9:24
     * @Param: [auth]
     * @return: void
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService)
                .passwordEncoder(new BCryptPasswordEncoder());// 实现自定义登录校验
    }

    /**
     * @Author: Galen
     * @Description: 配置放行的资源
     * @Date: 2019/3/28-9:23
     * @Param: [web]
     * @return: void
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login.html", "/index.html","/css/**", "/static/**", "/login_p", "/favicon.ico")
                // 给 swagger 放行；不需要权限能访问的资源
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");
    }

    /**
     * @Author: Galen
     * @Description: 拦截配置
     * @Date: 2019/4/4-10:44
     * @Param: [http]
     * @return: void
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 使其支持跨域
                .requestMatchers(CorsUtils :: isPreFlightRequest).permitAll()
                // 其他路径需要授权访问
                .anyRequest().authenticated()
                
                .and()
                .formLogin().loginPage("/login_p")// 设置登录页面
                //奇怪的是 这个url并没有被访问到。
                .loginProcessingUrl("/login")// 自定义的登录接口，这个接口必须和你登入页面form的action地址一样。否则一直进入login_p页面
                .usernameParameter("username").passwordParameter("password")//告诉security form表单用户名和密码的参数表达式。
                .failureHandler(new MyAuthenticationFailureHandler())
                .successHandler(new MyAuthenticationSuccessHandler())//如果不设置这个成功后的处理器，则会报错 999。
                .permitAll()
                
                .and()
                .csrf().disable(); //关闭csrf
    }
}