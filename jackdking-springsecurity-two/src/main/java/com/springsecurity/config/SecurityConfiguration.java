package com.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	// TODO Auto-generated method stub
//    	super.configure(auth);
    	//userdetail实现类bean也可以在这里注入到security中，
//    	auth.userDetailsService(userDetailsService());
    	//通过两种方式 用户认证信息放入到security中。
//    	auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("jack").password("{bcrypt}" +new BCryptPasswordEncoder().encode("88888888")).roles("USER");
    	auth.userDetailsService(userDetailsService());//覆盖了configure方法，那么覆盖userDetailsService()方法就失效了，因此root和guest都会失效，因此这里需要将UserDetail实现类bean注入到框架中。
    	auth.inMemoryAuthentication().withUser(User.withUsername("jack").password("{bcrypt}" + new BCryptPasswordEncoder().encode("88888888")).roles("USER"));
    	auth.inMemoryAuthentication().withUser(User.withUsername("king").password(passwordEncoder().encode("88888888")).roles("USER"));
    }
    

    //通过覆盖WebSecurityConfigurerAdapter的userDetailsService将实现类bean注入到security中。
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
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
        return manager;
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:on
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()  // 允许访问资源
                .antMatchers("/", "/home", "/about", "/login").permitAll() //允许访问这三个路由
                .antMatchers("/admin/**").hasAnyRole("ADMIN")   // 满足该条件下的路由需要ROLE_ADMIN的角色
                .antMatchers("/user/**").hasAnyRole("USER")     // 满足该条件下的路由需要ROLE_USER的角色
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .csrf().disable();
        // @formatter:off
    }
}
