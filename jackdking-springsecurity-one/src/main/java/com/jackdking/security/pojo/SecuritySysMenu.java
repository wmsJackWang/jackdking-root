package com.jackdking.security.pojo;

import java.util.List;

/**
 * 菜单表
 *
 * @author wcyong
 * @date 2019-04-18
 */
public class SecuritySysMenu {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单英文名称
     */
    private String titleEn;

    /**
     * 菜单图标
     */
    private String iconPic;

    /**
     * vue组件根路径
     */
    private String path;

    /**
     * vue的组件名
     */
    private String component;

    /**
     * 按钮id（页面级别唯一)
     */
    private String elementId;

    /**
     * 权限方式（默认0：允许；1：限制）
     */
    private Boolean onLimit;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 菜单的排序
     */
    private Integer sortOrder;

    private List<SecuritySysMenu> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic == null ? null : iconPic.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId == null ? null : elementId.trim();
    }

    public Boolean getOnLimit() {
        return onLimit;
    }

    public void setOnLimit(Boolean onLimit) {
        this.onLimit = onLimit;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<SecuritySysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SecuritySysMenu> children) {
        this.children = children;
    }
}