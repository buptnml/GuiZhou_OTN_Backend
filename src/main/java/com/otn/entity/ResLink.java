package com.otn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "res_link")
public class ResLink implements Serializable {
    /**
     * 链路ID
     */
    @Id
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 链路名
     */
    @Column(name = "link_name")
    private String linkName;

    /**
     * 链路类型
     */
    @Column(name = "link_type")
    private String linkType;

    /**
     * 所在环
     */
    @Column(name = "circle_id")
    private String circleId;

    /**
     * 链路长度
     */
    @Column(name = "link_length")
    private Float linkLength;

    /**
     * 链路损耗，单位为dBm
     */
    @Column(name = "link_loss")
    private Float linkLoss;

    /**
     * A端网元ID
     */
    @Column(name = "end_a_id")
    private Long endAId;

    /**
     * A端网元名称
     */
    @Column(name = "end_a_name")
    private String endAName;

    /**
     * Z端网元ID
     */
    @Column(name = "end_z_id")
    private Long endZId;

    /**
     * Z端网元名称
     */
    @Column(name = "end_z_name")
    private String endZName;

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
     * 获取链路ID
     *
     * @return link_id - 链路ID
     */
    public Long getLinkId() {
        return linkId;
    }

    /**
     * 设置链路ID
     *
     * @param linkId 链路ID
     */
    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    /**
     * 获取链路名
     *
     * @return link_name - 链路名
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * 设置链路名
     *
     * @param linkName 链路名
     */
    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
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
     * 获取所在环
     *
     * @return circle_id - 所在环
     */
    public String getCircleId() {
        return circleId;
    }

    /**
     * 设置所在环
     *
     * @param circleId 所在环
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    /**
     * 获取链路长度
     *
     * @return link_length - 链路长度
     */
    public Float getLinkLength() {
        return linkLength;
    }

    /**
     * 设置链路长度
     *
     * @param linkLength 链路长度
     */
    public void setLinkLength(Float linkLength) {
        this.linkLength = linkLength;
    }

    /**
     * 获取链路损耗，单位为dBm
     *
     * @return link_loss - 链路损耗，单位为dBm
     */
    public Float getLinkLoss() {
        return linkLoss;
    }

    /**
     * 设置链路损耗，单位为dBm
     *
     * @param linkLoss 链路损耗，单位为dBm
     */
    public void setLinkLoss(Float linkLoss) {
        this.linkLoss = linkLoss;
    }

    /**
     * 获取A端网元ID
     *
     * @return end_a_id - A端网元ID
     */
    public Long getEndAId() {
        return endAId;
    }

    /**
     * 设置A端网元ID
     *
     * @param endAId A端网元ID
     */
    public void setEndAId(Long endAId) {
        this.endAId = endAId;
    }

    /**
     * 获取A端网元名称
     *
     * @return end_a_name - A端网元名称
     */
    public String getEndAName() {
        return endAName;
    }

    /**
     * 设置A端网元名称
     *
     * @param endAName A端网元名称
     */
    public void setEndAName(String endAName) {
        this.endAName = endAName == null ? null : endAName.trim();
    }

    /**
     * 获取Z端网元ID
     *
     * @return end_z_id - Z端网元ID
     */
    public Long getEndZId() {
        return endZId;
    }

    /**
     * 设置Z端网元ID
     *
     * @param endZId Z端网元ID
     */
    public void setEndZId(Long endZId) {
        this.endZId = endZId;
    }

    /**
     * 获取Z端网元名称
     *
     * @return end_z_name - Z端网元名称
     */
    public String getEndZName() {
        return endZName;
    }

    /**
     * 设置Z端网元名称
     *
     * @param endZName Z端网元名称
     */
    public void setEndZName(String endZName) {
        this.endZName = endZName == null ? null : endZName.trim();
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
        ResLink other = (ResLink) that;
        return (this.getLinkId() == null ? other.getLinkId() == null : this.getLinkId().equals(other.getLinkId()))
            && (this.getLinkName() == null ? other.getLinkName() == null : this.getLinkName().equals(other.getLinkName()))
            && (this.getLinkType() == null ? other.getLinkType() == null : this.getLinkType().equals(other.getLinkType()))
            && (this.getCircleId() == null ? other.getCircleId() == null : this.getCircleId().equals(other.getCircleId()))
            && (this.getLinkLength() == null ? other.getLinkLength() == null : this.getLinkLength().equals(other.getLinkLength()))
            && (this.getLinkLoss() == null ? other.getLinkLoss() == null : this.getLinkLoss().equals(other.getLinkLoss()))
            && (this.getEndAId() == null ? other.getEndAId() == null : this.getEndAId().equals(other.getEndAId()))
            && (this.getEndAName() == null ? other.getEndAName() == null : this.getEndAName().equals(other.getEndAName()))
            && (this.getEndZId() == null ? other.getEndZId() == null : this.getEndZId().equals(other.getEndZId()))
            && (this.getEndZName() == null ? other.getEndZName() == null : this.getEndZName().equals(other.getEndZName()))
            && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLinkId() == null) ? 0 : getLinkId().hashCode());
        result = prime * result + ((getLinkName() == null) ? 0 : getLinkName().hashCode());
        result = prime * result + ((getLinkType() == null) ? 0 : getLinkType().hashCode());
        result = prime * result + ((getCircleId() == null) ? 0 : getCircleId().hashCode());
        result = prime * result + ((getLinkLength() == null) ? 0 : getLinkLength().hashCode());
        result = prime * result + ((getLinkLoss() == null) ? 0 : getLinkLoss().hashCode());
        result = prime * result + ((getEndAId() == null) ? 0 : getEndAId().hashCode());
        result = prime * result + ((getEndAName() == null) ? 0 : getEndAName().hashCode());
        result = prime * result + ((getEndZId() == null) ? 0 : getEndZId().hashCode());
        result = prime * result + ((getEndZName() == null) ? 0 : getEndZName().hashCode());
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
        sb.append(", linkId=").append(linkId);
        sb.append(", linkName=").append(linkName);
        sb.append(", linkType=").append(linkType);
        sb.append(", circleId=").append(circleId);
        sb.append(", linkLength=").append(linkLength);
        sb.append(", linkLoss=").append(linkLoss);
        sb.append(", endAId=").append(endAId);
        sb.append(", endAName=").append(endAName);
        sb.append(", endZId=").append(endZId);
        sb.append(", endZName=").append(endZName);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}