package com.jackdking.security.model;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 *
 * @author wcyong
 * @date 2019-04-19
 */
public class SysMenu implements Cloneable, Serializable {

    /**
     * @Author: Galen
     * @Description: 浅克隆
     * @Date: 2019/4/25-15:04
     * @Param: []
     * @return: java.lang.Object
     **/
    @Override
    public Object clone() {
        SysMenu sysMenu;
        try {
            sysMenu = (SysMenu) super.clone();
            return sysMenu;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 主键
     */
    private Long id;

    /**
     * 菜单类型（1：左侧主菜单；2：页面中的按钮；3：页面中标签）
     */
    private Integer menuType;

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
     * 是否有效（默认1：有效；0：无效；）
     */
    private Boolean enabled;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 菜单的排序
     */
    private Integer sortOrder;

    /**
     * 角色选择（前端使用）
     */
    private Boolean onChoose;

    private List<SysMenu> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getOnChoose() {
        return onChoose == null ? false : onChoose;
    }

    public void setOnChoose(Boolean onChoose) {
        this.onChoose = onChoose;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}