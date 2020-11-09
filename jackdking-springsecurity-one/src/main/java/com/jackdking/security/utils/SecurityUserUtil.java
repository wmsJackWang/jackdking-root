package com.jackdking.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jackdking.security.pojo.SecuritySysUser;

/**
 * @Author: Galen
 * @Date: 2019/4/4-9:38
 * @Description: 获取用户
 **/
public class SecurityUserUtil {
    /**
     * @Author: Galen
     * @Description: 从认证中心里面获取当前用户权限信息
     * @Date: 2019/3/28-9:49
     * @Param: []
     * @return: SecurityUser
     **/
    public static SecuritySysUser getCurrentUser() {
        return (SecuritySysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * @Author: Galen
     * @Description: 获取当前使用者的编号
     * @Date: 2019/4/8-17:11
     * @Param: []
     * @return: java.lang.Long
     **/
    public static Long getCurrentUserId() {
        SecuritySysUser securitySysUser = getCurrentUser();
        if (null == securitySysUser) {
            return null;
        }
        return securitySysUser.getId();
    }
}
