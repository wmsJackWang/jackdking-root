package com.jackdking.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @auther CodeGenerator
 * @create 2021-09-12 16:12:18
 * @describe 用户信息表实体类
 */
@TableName("sys_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="SysUser对象", description="用户信息表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
            @TableId(value = "user_id", type = IdType.AUTO)
                    private Long userId;

    @ApiModelProperty(value = "部门ID")
    @TableField("dept_id")
            private Long deptId;

    @ApiModelProperty(value = "登录账号")
    @TableField("login_name")
            private String loginName;

    @ApiModelProperty(value = "用户昵称")
    @TableField("user_name")
            private String userName;

    @ApiModelProperty(value = "用户类型（00系统用户 01注册用户）")
    @TableField("user_type")
            private String userType;

    @ApiModelProperty(value = "用户邮箱")
    @TableField("email")
            private String email;

    @ApiModelProperty(value = "手机号码")
    @TableField("phonenumber")
            private String phonenumber;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @TableField("sex")
            private String sex;

    @ApiModelProperty(value = "头像路径")
    @TableField("avatar")
            private String avatar;

    @ApiModelProperty(value = "密码")
    @TableField("password")
            private String password;

    @ApiModelProperty(value = "盐加密")
    @TableField("salt")
            private String salt;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    @TableField("status")
            private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    @TableField("del_flag")
            private String delFlag;

    @ApiModelProperty(value = "最后登陆IP")
    @TableField("login_ip")
            private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    @TableField("login_date")
            private Date loginDate;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
            private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
            private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
            private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
            private Date updateTime;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
            private String remark;


    public Long getUserId() {
        return userId;
    }

    public SysUser setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getDeptId() {
        return deptId;
    }

    public SysUser setDeptId(Long deptId) {
        this.deptId = deptId;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public SysUser setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SysUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public SysUser setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SysUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public SysUser setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public SysUser setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public SysUser setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public SysUser setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SysUser setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public SysUser setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public SysUser setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public SysUser setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public SysUser setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SysUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public SysUser setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SysUser setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SysUser setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", deptId=" + deptId +
                ", loginName=" + loginName +
                ", userName=" + userName +
                ", userType=" + userType +
                ", email=" + email +
                ", phonenumber=" + phonenumber +
                ", sex=" + sex +
                ", avatar=" + avatar +
                ", password=" + password +
                ", salt=" + salt +
                ", status=" + status +
                ", delFlag=" + delFlag +
                ", loginIp=" + loginIp +
                ", loginDate=" + loginDate +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", remark=" + remark +
        "}";
    }
}