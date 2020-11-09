package com.jackdking.security.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jackdking.security.model.SysRole;

/**
 * @Author: Galen
 * @Date: 2019/4/4-10:41
 * @Description:  适配 Security 权限
**/
public class SecuritySysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private String title;
    private List<SysRole> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
