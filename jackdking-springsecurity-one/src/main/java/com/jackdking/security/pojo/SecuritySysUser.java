package com.jackdking.security.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author: Galen
 * @Date: 2019/3/27-15:54
 * @Description: 实现UserDetails接口，
 * 适配 Security的用户
 **/
public class SecuritySysUser implements UserDetails {
    /**
     * 用户ID
     */
    private Long id;


    private Boolean enabled;

    private Boolean basicInfo;

    private String jsessionId;

    private Integer userType;

    private Integer crmId;

    private String nicknameCn;

    private String nicknameEn;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String userface;

    private List<SecuritySysRole> roles;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SecuritySysRole role : roles) {
            System.out.println(role.getId());
            System.out.println(role.getNameEn());
            if (null == role.getNameCn()) {
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(role.getNameEn()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public List<SecuritySysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecuritySysRole> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(Boolean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getCrmId() {
        return crmId;
    }

    public void setCrmId(Integer crmId) {
        this.crmId = crmId;
    }

    public String getNicknameCn() {
        return this.nicknameCn;
    }

    public void  setNickname(String nicknameCn) {
        this.nicknameCn = nicknameCn;
    }

    public String getNicknameEn() {
        return nicknameEn;
    }

    public void setNicknameEn(String nicknameEn) {
        this.nicknameEn = nicknameEn;
    }

	@Override
	public String toString() {
		return "SecuritySysUser [id=" + id + ", enabled=" + enabled + ", basicInfo=" + basicInfo + ", jsessionId="
				+ jsessionId + ", userType=" + userType + ", crmId=" + crmId + ", nicknameCn=" + nicknameCn
				+ ", nicknameEn=" + nicknameEn + ", username=" + username + ", password=" + password + ", userface="
				+ userface + ", roles=" + roles + "]";
	}
    
}