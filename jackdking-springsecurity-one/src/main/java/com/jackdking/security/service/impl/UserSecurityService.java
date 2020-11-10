package com.jackdking.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackdking.security.mapper.UserSecurityMapper;
import com.jackdking.security.pojo.SecuritySysRole;
import com.jackdking.security.pojo.SecuritySysUser;

/**
 * @Author: Galen
 * @Date: 2019/4/3-17:19
 * @Description: security权限管理框架
 **/
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserSecurityService.class);

//
//    @Autowired
//    private UserSecurityMapper userSecurityMapper;

    /**
     * @Author: Galen
     * @Description: 实现了UserDetailsService接口中的loadUserByUsername方法
     * 执行登录,构建Authentication对象必须的信息,
     * 如果用户不存在，则抛出UsernameNotFoundException异常
     * @Date: 2019/3/27-16:04
     * @Param: [s]
     * @return: org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * @Author: Galen
         * @Description: 查询数据库，获取登录的用户信息
         **/
//        SecuritySysUser securityUser = userSecurityMapper.loadUserByUsername(username);
        
    	SecuritySysUser securityUser = new SecuritySysUser();
    	securityUser.setUsername(username);
    	securityUser.setPassword("$2a$10$4H1JSQxyrJlguu0/V4DnR.s2NBjE.k6rI6.W.1AFL0UEnR2IR2/5y");
    	securityUser.setEnabled(true);
    	
    	List<SecuritySysRole> roles = new ArrayList<>();
    	SecuritySysRole role = new SecuritySysRole();
    	role.setNameCn("ROLE_ADMIN");
    	securityUser.setRoles(roles);
        
        if (securityUser == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        
        log.info("用户信息：{}" , securityUser.toString());
        
        return securityUser;
    }
}
