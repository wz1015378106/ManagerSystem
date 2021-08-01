package com.system.manager.entity.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_menu_v")
public class RoleMenuVEntity {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    private String id;
    /**
     * 角色编码
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;
    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME")
    private String roleName;
    /**
     * 菜单编码
     */
    @Column(name = "MENU_CODE")
    private String menuCode;
    /**
     * 菜单名称
     */
    @Column(name = "MENU_NAME")
    private String menuName;
    /**
     * 角色ID
     */
    @Column(name = "ROLE_ID")
    private String roleId;
    /**
     * 菜单ID
     */
    @Column(name = "MENU_ID")
    private String menuId;
    /**
     * 菜单URL
     */
    @Column(name = "MENU_URL")
    private String menuUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
}
