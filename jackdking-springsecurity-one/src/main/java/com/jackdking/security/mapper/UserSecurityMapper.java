package com.jackdking.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jackdking.security.pojo.SecuritySysMenu;
import com.jackdking.security.pojo.SecuritySysPermission;
import com.jackdking.security.pojo.SecuritySysUser;

/**
 * Created by sang on 2017/12/28.
 */
@Repository
public interface UserSecurityMapper {
    /**
     * @Author: Galen
     * @Description: 查询数据库用户
     * @Date: 2019/4/18-19:29
     * @Param: [username]
     * @return: com.apl.tl.pojo.SecuritySysUser
     **/
    SecuritySysUser loadUserByUsername(String username);

    /**
     * @Author: Galen
     * @Description: 获取所有的权限菜单
     * @Date: 2019/4/26-15:14
     * @Param: []
     * @return: java.util.List<com.galen.security.pojo.SecuritySysPermission>
     **/
    List<SecuritySysPermission> getAllPermission();


    /**
     * @Author: Galen
     * @Description: 获取此用户的权限菜单
     * @Date: 2019/4/18-16:20
     * @Param: [userId]
     * @return: java.util.List<com.apl.pgs.mini.pojo.SecuritySysMenu>
     **/
    List<SecuritySysMenu> getMenuByUid(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取当前用户的当前菜单页面的按钮
     * @Date: 2019/4/19-11:43
     * @Param: [userId, menuId]
     * @return: java.util.List<java.lang.String>
     **/
    List<String> getButtonElementIdByUid(@Param("userId") Long userId, @Param("menuId") Long menuId);
}
