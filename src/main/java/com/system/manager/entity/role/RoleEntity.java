package com.system.manager.entity.role;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "ID")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
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
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private String createDate;
    /**
     * 有效标识
     */
    @Column(name = "ENABLED_FLAG")
    private String enabledFlag;
    /**
     * 查看数据权限，0 全部数据 1 自己的数据
     */
   /* @Column(name = "FIND_DATA_PERMISSION")
    private String findDataPermission;*/

    /**
     * 角色的菜单资源
     */
    @Transient
    private List<String> menuList;


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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }
}
