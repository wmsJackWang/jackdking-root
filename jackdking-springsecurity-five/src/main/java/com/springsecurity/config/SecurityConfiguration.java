package com.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.filter.AdminFilter;
import com.springsecurity.filter.CommonFilter;
import com.springsecurity.filter.UserFilter;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    
    @Autowired
    private DataSource dataSource;
   

    /*
     * 内存中保存用户信息  测试 一
     * 使用 auth.inMemoryAuthentication().withUser api情况
     * 下面三种用户信息，只有root用户登入正常，其他用户名登入后，页面都会报错，但正常登入了，即从新刷新后，显示成功登入后的用户信息
     * 其他用户名会报错: security.core.userdetails.UsernameNotFoundException: jack(king) , 但重新进入页面发现是登入成功了的。
     * 
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
////    	auth.userDetailsService(userDetailsService());//配置userdetail必不可少
//    	
//    	auth.inMemoryAuthentication().withUser(User.withUsername("jack").password("{bcrypt}" + new BCryptPasswordEncoder().encode("88888888")).roles("USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("king").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("root").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
//    }
    
    
    /*
     * 下面userDetailsService方法一起使用，root用户使用第一个方法的密码跟第二个方法的密码都能正常登录。
     * 其他用户名 还是会出现 错误： security.core.userdetails.UsernameNotFoundException: jack(king) , 但重新进入页面发现是登入成功了的。
     * 
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
//    	auth.userDetailsService(userDetailsService());//配置userdetail必不可少
//    	
//    	auth.inMemoryAuthentication().withUser(User.withUsername("jack").password("{bcrypt}" + new BCryptPasswordEncoder().encode("88888888")).roles("USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("king").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("root").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
//    }
//    
//    public UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//    	 //直接建两个用户存在内存中，生产环境可以从数据库中读取,对应管理器JdbcUserDetailsManager
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        // 创建两个用户
//        //通过密码的前缀区分编码方式,推荐,这种加密方式很好的利用了委托者模式，使得程序可以使用多种加密方式，并且会自动
//        //根据前缀找到对应的密码编译器处理。
//        manager.createUser(User.withUsername("guest").password("{bcrypt}" +
//                new BCryptPasswordEncoder().encode("111111")).roles("USER").build());
//        manager.createUser(User.withUsername("root").password("{sha256}" +
//                new StandardPasswordEncoder().encode("666666"))
//                .roles("ADMIN", "USER").build());
//        System.out.println("获取用户信息：userDetailsService");
//        return manager;
//	}
    
    
    
    /*
     * 不使用 auth.inMemoryAuthentication().withUser的api，只是用InMemoryUserDetailsManager方法。
     * 
     *  InMemoryUserDetailsManager 类的方法 能让方法都能正常 登录，没有任何错误。
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	auth.userDetailsService(userDetailsService());//配置userdetail必不可少
    	
//    	auth.inMemoryAuthentication().withUser(User.withUsername("jack").password("{bcrypt}" + new BCryptPasswordEncoder().encode("88888888")).roles("USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("king").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
//    	auth.inMemoryAuthentication().withUser(User.withUsername("root").password(passwordEncoder().encode("88888888")).roles("ADMIN","USER"));
    }
    
    public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
    	 //直接建两个用户存在内存中，生产环境可以从数据库中读取,对应管理器JdbcUserDetailsManager
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 创建两个用户
        //通过密码的前缀区分编码方式,推荐,这种加密方式很好的利用了委托者模式，使得程序可以使用多种加密方式，并且会自动
        //根据前缀找到对应的密码编译器处理。
        manager.createUser(User.withUsername("guest").password("{bcrypt}" +
                new BCryptPasswordEncoder().encode("111111")).roles("USER").build());
        manager.createUser(User.withUsername("root").password("{sha256}" +
                new StandardPasswordEncoder().encode("666666"))
                .roles("ADMIN", "USER").build());
        System.out.println("获取用户信息：userDetailsService");
        return manager;
	}
    
    

	/**
     * 支持多种编码，通过密码的前缀区分编码方式,推荐
     *
     * @return the password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
    	
    	PasswordEncoder PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	PasswordEncoder.upgradeEncoding("{sha256}");
        return PasswordEncoder;
    }
    
//    
//    public static PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        
//        tokenRepository.setDataSource(dataSource);
//        // 如果token表不存在，使用下面语句可以初始化该表；若存在，会报错。
////        tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }
    
    @Configuration
    @Order(3)
    static class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
            .authorizeRequests()
//            .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()  // 允许访问资源
//            .antMatchers("/", "/home", "/about", "/login").permitAll() //允许访问这三个路由
            .antMatchers("/admin/**").hasAnyRole("ADMIN001")   // 满足该条件下的路由需要ROLE_ADMIN的角色
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
//            .loginProcessingUrl("/login")
//            .usernameParameter("username")
//            .passwordParameter("password")
//            .successForwardUrl("/admin")
            .defaultSuccessUrl("/admin/admin")//默认的成功跳转到的url
            .failureUrl("/403")
            .and()
            .rememberMe()
            .and()
            .logout()
            .permitAll()
            .and()
            .csrf().disable();
            
            http.addFilterBefore(new AdminFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }
    
    @Configuration
    @Order(2)
    static class UserWebSecurityConfig extends WebSecurityConfigurerAdapter {
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:on
            http.antMatcher("/user/**")
            		.authorizeRequests()
//                    .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()  // 允许访问资源
//                    .antMatchers("/", "/home", "/about", "/login").permitAll() //允许访问这三个路由
                    .antMatchers("/user/**").hasAnyRole("USER")     // 满足该条件下的路由需要ROLE_USER的角色
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
//                    .loginProcessingUrl("/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .successForwardUrl("/admin")
                    .defaultSuccessUrl("/user/user")//默认的成功跳转到的url
                    .failureUrl("/403")
                    .and()
                    .rememberMe()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .csrf().disable();

            http.addFilterBefore(new UserFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }
    
    
    @Configuration
    @Order(4)
    static class CommonWebSecurityConfig extends WebSecurityConfigurerAdapter {
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:on
            http.authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()  // 允许访问资源
                    .antMatchers("/", "/home", "/about", "/login").permitAll() //允许访问这三个路由
                    .antMatchers("/user/**").hasAnyRole("USER")     // 满足该条件下的路由需要ROLE_USER的角色
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")   // 满足该条件下的路由需要ROLE_ADMIN的角色
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
//                    .loginProcessingUrl("/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .successForwardUrl("/admin")
                    .defaultSuccessUrl("/admin/admin")//默认的成功跳转到的url
                    .failureUrl("/403")
                    .and()
                    .rememberMe()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .csrf().disable();

            http.addFilterBefore(new CommonFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
