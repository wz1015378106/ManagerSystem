package com.sysytem.manager.entity.role;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 角色菜单映射表
 */
@Entity
@Table(name = "role_menu")
public class RoleMenuEntity {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;
    /**
     * 菜单id
     */
    @Column(name = "MENU_ID")
    private String menuId;
    /**
     * 角色id
     */
    @Column(name = "ROLE_ID")
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
