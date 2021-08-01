package com.system.manager.entity.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Table(name = "USER")
@Entity(name = "UserEntity")
public class UserEntity implements Serializable {
    /**
     * 主键ID，自动生成uuid
     */
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id")
    private String id;
    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * 密码，暂时用明文存储，一切从简
     */
    @Column(name = "PASSWORD")
    private String password;
    /**
     * 住址
     */
    @Column(name = "ADDRESS")
    private String address;
    /**
     * 邮箱地址
     */
    @Column(name = "EMAIL")
    private String email;
    /**
     * 工号
     */
    @Column(name = "USER_CODE")
    private String userCode;
    /**
     * 角色id(主)
     */
    @Column(name = "MAIN_ROLE_ID")
    private String roleId;
    /**
     * 有效标识
     */
    @Column(name = "ENABLED_FLAG")
    private String enabledFlag;
    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private String createDate;
    /**
     * 角色（副）
     */
    @Column(name = "ASSIST_ROLE_ID")
    private String assistRoleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAssistRoleId() {
        return assistRoleId;
    }

    public void setAssistRoleId(String assistRoleId) {
        this.assistRoleId = assistRoleId;
    }

}
