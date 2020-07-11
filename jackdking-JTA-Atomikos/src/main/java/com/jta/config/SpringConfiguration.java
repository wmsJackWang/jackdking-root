package com.jta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jta.service.PeopleService;
import com.jta.service.UserService;
import com.jta.service.impl.PeopleServiceImpl;
import com.jta.service.impl.UserServiceImpl;

 
@Configuration
public class SpringConfiguration {
    /**
     * User service user service.
     *
     * @return the user service
     */
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    /**
     * People service people service.
     *
     * @return the people service
     */
    @Bean
    public PeopleService peopleService() {
        return new PeopleServiceImpl();
    }
}

