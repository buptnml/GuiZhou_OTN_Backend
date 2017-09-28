package com.bupt.entity;


import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_version_dict")
public class SysVersionDict {
    /**
     * 版本字典id
     */
    @Id
    @Column(name = "version_dict_id")
    private Long versionDictId;

    /**
     * 版本字典名称
     */
    @Column(name = "version_dict_name")
    private String versionDictName;

    /**
     * 版本字典名称
     */
    @Column(name = "version_dict_description")
    private String versionDictDescription;

    /**
     * 创建者
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 是否有业务表
     */
    @Column(name = "has_bussiness")
    private Boolean hasBussiness;

    /**
     * 是否有板卡表
     */
    @Column(name = "has_disk")
    private Boolean hasDisk;

    /**
     * 是否有链路表
     */
    @Column(name = "has_link")
    private Boolean hasLink;

    /**
     * 是否有网元表
     */
    @Column(name = "has_net_element")
    private Boolean hasNetElement;

    /**
     * 是否有链路类型表
     */
    @Column(name = "has_link_type")
    private Boolean hasLinkType;

    /**
     * 是否有放大器表
     */
    @Column(name = "has_amplifier")
    private Boolean hasAmplifier;

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
     * 获取版本字典id
     *
     * @return version_dict_id - 版本字典id
     */
    public Long getVersionDictId() {
        return versionDictId;
    }

    /**
     * 设置版本字典id
     *
     * @param versionDictId 版本字典id
     */
    public void setVersionDictId(Long versionDictId) {
        this.versionDictId = versionDictId;
    }

    /**
     * 获取版本字典名称
     *
     * @return version_dict_name - 版本字典名称
     */
    public String getVersionDictName() {
        return versionDictName;
    }

    /**
     * 设置版本字典名称
     *
     * @param versionDictName 版本字典名称
     */
    public void setVersionDictName(String versionDictName) {
        this.versionDictName = versionDictName == null ? null : versionDictName.trim();
    }

    /**
     * 获取版本字典名称
     *
     * @return version_dict_description - 版本字典名称
     */
    public String getVersionDictDescription() {
        return versionDictDescription;
    }

    /**
     * 设置版本字典名称
     *
     * @param versionDictDescription 版本字典名称
     */
    public void setVersionDictDescription(String versionDictDescription) {
        this.versionDictDescription = versionDictDescription == null ? null : versionDictDescription.trim();
    }

    /**
     * 获取创建者
     *
     * @return creator_name - 创建者
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * 设置创建者
     *
     * @param creatorName 创建者
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**
     * 获取是否有业务表
     *
     * @return has_bussiness - 是否有业务表
     */
    public Boolean getHasBussiness() {
        return hasBussiness;
    }

    /**
     * 设置是否有业务表
     *
     * @param hasBussiness 是否有业务表
     */
    public void setHasBussiness(Boolean hasBussiness) {
        this.hasBussiness = hasBussiness;
    }

    /**
     * 获取是否有板卡表
     *
     * @return has_disk - 是否有板卡表
     */
    public Boolean getHasDisk() {
        return hasDisk;
    }

    /**
     * 设置是否有板卡表
     *
     * @param hasDisk 是否有板卡表
     */
    public void setHasDisk(Boolean hasDisk) {
        this.hasDisk = hasDisk;
    }

    /**
     * 获取是否有链路表
     *
     * @return has_link - 是否有链路表
     */
    public Boolean getHasLink() {
        return hasLink;
    }

    /**
     * 设置是否有链路表
     *
     * @param hasLink 是否有链路表
     */
    public void setHasLink(Boolean hasLink) {
        this.hasLink = hasLink;
    }

    /**
     * 获取是否有网元表
     *
     * @return has_net_element - 是否有网元表
     */
    public Boolean getHasNetElement() {
        return hasNetElement;
    }

    /**
     * 设置是否有网元表
     *
     * @param hasNetElement 是否有网元表
     */
    public void setHasNetElement(Boolean hasNetElement) {
        this.hasNetElement = hasNetElement;
    }

    /**
     * 获取是否有链路类型表
     *
     * @return has_link_type - 是否有链路类型表
     */
    public Boolean getHasLinkType() {
        return hasLinkType;
    }

    /**
     * 设置是否有链路类型表
     *
     * @param hasLinkType 是否有链路类型表
     */
    public void setHasLinkType(Boolean hasLinkType) {
        this.hasLinkType = hasLinkType;
    }

    /**
     * 获取是否有放大器表
     *
     * @return has_amplifier - 是否有放大器表
     */
    public Boolean getHasAmplifier() {
        return hasAmplifier;
    }

    /**
     * 设置是否有放大器表
     *
     * @param hasAmplifier 是否有放大器表
     */
    public void setHasAmplifier(Boolean hasAmplifier) {
        this.hasAmplifier = hasAmplifier;
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
}