package com.jta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jta.mapper.spring.UserMapper;
import com.jta.model.spring.User;
import com.jta.service.UserService;


 
@Service
public class UserServiceImpl implements UserService {
    /**
     * The User mapper.
     */
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.findAll();
    }

    @Override
    public Boolean insertUser(User user) {
        return userMapper.insertSelective(user);
    }
}
