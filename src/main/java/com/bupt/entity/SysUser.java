package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "pass_word")
    private String passWord;

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
     * 最后更新时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return pass_word - 用户密码
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置用户密码
     *
     * @param passWord 用户密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
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
     * 获取最后更新时间
     *
     * @return gmt_modified - 最后更新时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置最后更新时间
     *
     * @param gmtModified 最后更新时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}