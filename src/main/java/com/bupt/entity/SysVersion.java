package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_version")
public class SysVersion {
    /**
     * 版本id
     */
    @Id
    @Column(name = "version_id")
    private Long versionId;

    /**
     * 版本名称
     */
    @Column(name = "version_name")
    private String versionName;

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
     * 版本详细设置
     */
    @Column(name = "version_setting")
    private byte[] versionSetting;

    /**
     * 获取版本id
     *
     * @return version_id - 版本id
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * 设置版本id
     *
     * @param versionId 版本id
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * 获取版本名称
     *
     * @return version_name - 版本名称
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 设置版本名称
     *
     * @param versionName 版本名称
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
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
     * 获取版本详细设置
     *
     * @return version_setting - 版本详细设置
     */
    public byte[] getVersionSetting() {
        return versionSetting;
    }

    /**
     * 设置版本详细设置
     *
     * @param versionSetting 版本详细设置
     */
    public void setVersionSetting(byte[] versionSetting) {
        this.versionSetting = versionSetting;
    }
}