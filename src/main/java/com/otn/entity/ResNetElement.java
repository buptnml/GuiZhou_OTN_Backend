package com.otn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "res_net_element")
public class ResNetElement implements Serializable {
    /**
     * 网元ID
     */
    @Id
    @Column(name = "net_element_id")
    private Long netElementId;

    /**
     * 网元名
     */
    @Column(name = "net_element_name")
    private String netElementName;

    /**
     * 网元型号
     */
    @Column(name = "net_element_type")
    private String netElementType;

    /**
     * 网元坐标x
     */
    @Column(name = "coordinate_x")
    private Float coordinateX;

    /**
     * 网元坐标y
     */
    @Column(name = "coordinate_y")
    private Float coordinateY;

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
     * 获取网元ID
     *
     * @return net_element_id - 网元ID
     */
    public Long getNetElementId() {
        return netElementId;
    }

    /**
     * 设置网元ID
     *
     * @param netElementId 网元ID
     */
    public void setNetElementId(Long netElementId) {
        this.netElementId = netElementId;
    }

    /**
     * 获取网元名
     *
     * @return net_element_name - 网元名
     */
    public String getNetElementName() {
        return netElementName;
    }

    /**
     * 设置网元名
     *
     * @param netElementName 网元名
     */
    public void setNetElementName(String netElementName) {
        this.netElementName = netElementName == null ? null : netElementName.trim();
    }

    /**
     * 获取网元型号
     *
     * @return net_element_type - 网元型号
     */
    public String getNetElementType() {
        return netElementType;
    }

    /**
     * 设置网元型号
     *
     * @param netElementType 网元型号
     */
    public void setNetElementType(String netElementType) {
        this.netElementType = netElementType == null ? null : netElementType.trim();
    }

    /**
     * 获取网元坐标x
     *
     * @return coordinate_x - 网元坐标x
     */
    public Float getCoordinateX() {
        return coordinateX;
    }

    /**
     * 设置网元坐标x
     *
     * @param coordinateX 网元坐标x
     */
    public void setCoordinateX(Float coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * 获取网元坐标y
     *
     * @return coordinate_y - 网元坐标y
     */
    public Float getCoordinateY() {
        return coordinateY;
    }

    /**
     * 设置网元坐标y
     *
     * @param coordinateY 网元坐标y
     */
    public void setCoordinateY(Float coordinateY) {
        this.coordinateY = coordinateY;
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
        ResNetElement other = (ResNetElement) that;
        return (this.getNetElementId() == null ? other.getNetElementId() == null : this.getNetElementId().equals(other.getNetElementId()))
            && (this.getNetElementName() == null ? other.getNetElementName() == null : this.getNetElementName().equals(other.getNetElementName()))
            && (this.getNetElementType() == null ? other.getNetElementType() == null : this.getNetElementType().equals(other.getNetElementType()))
            && (this.getCoordinateX() == null ? other.getCoordinateX() == null : this.getCoordinateX().equals(other.getCoordinateX()))
            && (this.getCoordinateY() == null ? other.getCoordinateY() == null : this.getCoordinateY().equals(other.getCoordinateY()))
            && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNetElementId() == null) ? 0 : getNetElementId().hashCode());
        result = prime * result + ((getNetElementName() == null) ? 0 : getNetElementName().hashCode());
        result = prime * result + ((getNetElementType() == null) ? 0 : getNetElementType().hashCode());
        result = prime * result + ((getCoordinateX() == null) ? 0 : getCoordinateX().hashCode());
        result = prime * result + ((getCoordinateY() == null) ? 0 : getCoordinateY().hashCode());
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
        sb.append(", netElementId=").append(netElementId);
        sb.append(", netElementName=").append(netElementName);
        sb.append(", netElementType=").append(netElementType);
        sb.append(", coordinateX=").append(coordinateX);
        sb.append(", coordinateY=").append(coordinateY);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}