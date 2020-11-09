package com.jackdking.security.model;

/**
 * 权限-角色关联
 * 
 * @author wcyong
 * 
 * @date 2019-04-03
 */
public class SysPermissionRole {
    private Long id;

    private Long permissionId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}