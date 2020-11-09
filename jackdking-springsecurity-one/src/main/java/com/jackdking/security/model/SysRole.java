package com.jackdking.security.model;

/**
 * 角色表
 *
 * @author wcyong
 * @date 2019-04-12
 */
public class SysRole {
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

    /**
     * 角色选择（前端使用）
     */
    private Boolean onChoose;

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
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
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

    public Boolean getOnChoose() {
        return onChoose == null ? false : onChoose;
    }

    public void setOnChoose(Boolean onChoose) {
        this.onChoose = onChoose;
    }
}