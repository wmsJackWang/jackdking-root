package com.jackdking.security.service;

import com.jackdking.security.model.SysUser;
import com.jackdking.security.pojo.GalenResponse;

public interface UserService {
    /**
     * @Author: Galen
     * @Description: 添加用户
     * @Date: 2019/4/10-9:26
     * @Param: [sysUser]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    GalenResponse createUser(SysUser sysUser);
}
