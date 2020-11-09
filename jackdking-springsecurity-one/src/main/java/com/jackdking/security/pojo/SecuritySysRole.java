package com.jackdking.security.pojo;

import java.io.Serializable;

/**
 * @Author: Galen
 * @Date: 2019/4/12-15:46
 * @Description: 适配 Security的角色
 **/
public class SecuritySysRole implements Serializable {

    private Long id;
    /**
     * 角色英文名称
     */
    private String nameEn;
    /**
     * 角色中文名称
     */
    private String nameCn;
    /**
     * 角色类型
     */
    private Integer groupType;

    /**
     * 角色独立
     */
    private Boolean onAlone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Boolean getOnAlone() {
        return onAlone;
    }

    public void setOnAlone(Boolean onAlone) {
        this.onAlone = onAlone;
    }
}
