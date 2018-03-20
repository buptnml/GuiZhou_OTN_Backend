package com.otn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "res_onsr_link_type")
public class ResOnsrLinkType implements Serializable {
    /**
     * 链路类型ID
     */
    @Id
    @Column(name = "link_type_id")
    private Long linkTypeId;

    /**
     * 链路类型
     */
    @Column(name = "link_type")
    private String linkType;

    /**
     * 链路损耗，单位dB/km
     */
    @Column(name = "link_loss")
    private Float linkLoss;

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

    private static final long serialVersionUID = 1L;

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
     * @return link_type - 链路类型
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * 设置链路类型
     *
     * @param linkType 链路类型
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
    }

    /**
     * 获取链路损耗，单位dB/km
     *
     * @return link_loss - 链路损耗，单位dB/km
     */
    public Float getLinkLoss() {
        return linkLoss;
    }

    /**
     * 设置链路损耗，单位dB/km
     *
     * @param linkLoss 链路损耗，单位dB/km
     */
    public void setLinkLoss(Float linkLoss) {
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ResOnsrLinkType other = (ResOnsrLinkType) that;
        return (this.getLinkTypeId() == null ? other.getLinkTypeId() == null : this.getLinkTypeId().equals(other.getLinkTypeId()))
            && (this.getLinkType() == null ? other.getLinkType() == null : this.getLinkType().equals(other.getLinkType()))
            && (this.getLinkLoss() == null ? other.getLinkLoss() == null : this.getLinkLoss().equals(other.getLinkLoss()))
            && (this.getLinkRate() == null ? other.getLinkRate() == null : this.getLinkRate().equals(other.getLinkRate()))
            && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLinkTypeId() == null) ? 0 : getLinkTypeId().hashCode());
        result = prime * result + ((getLinkType() == null) ? 0 : getLinkType().hashCode());
        result = prime * result + ((getLinkLoss() == null) ? 0 : getLinkLoss().hashCode());
        result = prime * result + ((getLinkRate() == null) ? 0 : getLinkRate().hashCode());
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", linkTypeId=").append(linkTypeId);
        sb.append(", linkType=").append(linkType);
        sb.append(", linkLoss=").append(linkLoss);
        sb.append(", linkRate=").append(linkRate);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}