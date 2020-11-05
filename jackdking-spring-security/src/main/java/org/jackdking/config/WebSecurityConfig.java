package org.jackdking.config;

import org.jackdking.security.JwtAccessDeniedHandler;
import org.jackdking.security.JwtAuthenticationEntryPoint;
import org.jackdking.security.UserModelDetailsService;
import org.jackdking.security.jwt.JWTConfigurer;
import org.jackdking.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }
   
   //可以通过 覆盖这个方法  设置userdetail服务，自定义获取用户认证数据的实现类
   @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//	    auth.userDetailsService(new UserModelDetailsService(null));
		super.configure(auth);
	}

   // Configure BCrypt password encoder =====================================================================

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configure paths and requests that should be ignored by Spring Security ================================
   //配置一些地址     不走 Spring Security 过滤器链
   //主要是一些 静态资源的地址
   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/h2-console/**"
         );
   }

   // Configure security settings ===========================================================================

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         // we don't need CSRF because our token is invulnerable
      	 //添加 CSRF 支持，使用WebSecurityConfigurerAdapter时，默认启用
         .csrf().disable()

         //在认证过滤器之前添加 corsFilter
         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         //允许配置 错误处理
         .exceptionHandling()
         //错误处理
         .authenticationEntryPoint(authenticationErrorHandler)
         //拒绝处理
         .accessDeniedHandler(jwtAccessDeniedHandler)

         // enable h2-console
         .and()
         .headers()//将安全标头添加到响应
         .frameOptions()
         .sameOrigin()
         
         
         //################################################################################
         //默认的表单登入 ：使用默认的表单登录页面和登录端点/login进行登录
//         .and()
//         .formLogin()
//         .defaultSuccessUrl("/toIndex",false)
//         .permitAll()
         //################################################################################
         
         
         //################################################################################
         // 设置rememberMe(记住我)功能， 设置了remember功能，用户登入后会 给浏览器一个token，这个token
         // 设置了60分钟过期时间，用户关闭浏览器后60分钟内 再访问网站就不用再重新认证。
//         .and()
//         .rememberMe()
//         .tokenValiditySeconds(3600)
         //################################################################################
         
         
         
         
         
         //################################################################################
         // 权限配置
//         .and()
//         .authorizeRequests()//开启配置路径的权限
//         .antMatchers("/api/info1","/api/info2","/api/authenticate")
//         .permitAll()//设置这3个 url 不用登入拦截
//         
//         .anyRequest()//设置其他的任何请求都要拦截
//         .authenticated()
         //################################################################################

         
         //################################################################################
         //登出 接口 不用登入拦截。
//         .and()
//         .logout()
//         .permitAll()
         //################################################################################

         //################################################################################
         //配置某个url  ， 只有用户拥有某个权限后  才能通过访问。
//         .and()
//         .authorizeRequests()
//         .antMatchers("url").hasAuthority("ROLE_ADMIN")//该url只允许拥有权限ROLE_ADMIN请求
         //################################################################################
         
         
         // create no session
         .and()
         .sessionManagement()//允许配置会话管理
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//会话创建策略  : 不会创建也不会使用session

         .and()
         .authorizeRequests()//允许基于使用HttpServletRequest限制访问
         .antMatchers("/api/authenticate").permitAll()//配置 url的权限，该url允许所有人访问
         // .antMatchers("/api/register").permitAll()
         // .antMatchers("/api/activate").permitAll()
         // .antMatchers("/api/account/reset-password/init").permitAll()
         // .antMatchers("/api/account/reset-password/finish").permitAll()

         .antMatchers("/api/person").hasAuthority("ROLE_USER")//该url只允许拥有权限ROLE_USER请求
         .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")//该url只允许拥有权限ROLE_ADMIN请求

         .anyRequest().authenticated()////表示剩余的其他接口，登录之后就能访问

         .and()//获取httpSecurity的builder，构建httpSecurity对象
         //再添加一个配置bean ，通过适配器模式将SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>对象
         //放入到
         .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
