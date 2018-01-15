package com.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "res_onsr_link_info")
public class ResOnsrLinkInfo {
    /**
     * 链路类型ID
     */
    @Id
    @Column(name = "link_type_id")
    private Long linkTypeId;

    /**
     * 链路类型
     */
    @Column(name = "link_type_name")
    private String linkTypeName;

    /**
     * 链路损耗，单位dB/km
     */
    @Column(name = "link_loss")
    private Short linkLoss;

    /**
     * 链路速率
     */
    @Column(name = "link_rate")
    private String linkRate;

    /**
     * 条目所在版本ID
     */
    @Column(name = "version_id")
    private Long versionId;

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
     * 获取链路类型ID
     *
     * @return link_type_id - 链路类型ID
     */
    public Long getLinkTypeId() {
        return linkTypeId;
    }

    /**
     * 设置链路类型ID
     *
     * @param linkTypeId 链路类型ID
     */
    public void setLinkTypeId(Long linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    /**
     * 获取链路类型
     *
     * @return link_type_name - 链路类型
     */
    public String getLinkTypeName() {
        return linkTypeName;
    }

    /**
     * 设置链路类型
     *
     * @param linkTypeName 链路类型
     */
    public void setLinkTypeName(String linkTypeName) {
        this.linkTypeName = linkTypeName == null ? null : linkTypeName.trim();
    }

    /**
     * 获取链路损耗，单位dB/km
     *
     * @return link_loss - 链路损耗，单位dB/km
     */
    public Short getLinkLoss() {
        return linkLoss;
    }

    /**
     * 设置链路损耗，单位dB/km
     *
     * @param linkLoss 链路损耗，单位dB/km
     */
    public void setLinkLoss(Short linkLoss) {
        this.linkLoss = linkLoss;
    }

    /**
     * 获取链路速率
     *
     * @return link_rate - 链路速率
     */
    public String getLinkRate() {
        return linkRate;
    }

    /**
     * 设置链路速率
     *
     * @param linkRate 链路速率
     */
    public void setLinkRate(String linkRate) {
        this.linkRate = linkRate == null ? null : linkRate.trim();
    }

    /**
     * 获取条目所在版本ID
     *
     * @return version_id - 条目所在版本ID
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * 设置条目所在版本ID
     *
     * @param versionId 条目所在版本ID
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
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