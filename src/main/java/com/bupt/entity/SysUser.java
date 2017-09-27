package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户身份
     */
    @Column(name = "user_role")
    private String userRole;

    /**
     * 用户所属组织
     */
    @Column(name = "user_group")
    private String userGroup;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 用户的设置文件，Java的二进制流
     */
    @Column(name = "user_setting")
    private byte[] userSetting;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取用户身份
     *
     * @return user_role - 用户身份
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * 设置用户身份
     *
     * @param userRole 用户身份
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    /**
     * 获取用户所属组织
     *
     * @return user_group - 用户所属组织
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * 设置用户所属组织
     *
     * @param userGroup 用户所属组织
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup == null ? null : userGroup.trim();
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取最后修改时间
     *
     * @return gmt_modified - 最后修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置最后修改时间
     *
     * @param gmtModified 最后修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取用户的设置文件，Java的二进制流
     *
     * @return user_setting - 用户的设置文件，Java的二进制流
     */
    public byte[] getUserSetting() {
        return userSetting;
    }

    /**
     * 设置用户的设置文件，Java的二进制流
     *
     * @param userSetting 用户的设置文件，Java的二进制流
     */
    public void setUserSetting(byte[] userSetting) {
        this.userSetting = userSetting;
    }
}