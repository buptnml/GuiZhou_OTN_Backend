package com.bupt.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "res_disk")
public class ResDisk implements Serializable {
    /**
     * 机盘ID
     */
    @Id
    @Column(name = "disk_id")
    private Long diskId;

    @Column(name = "slot_id")
    private Long slotId;

    /**
     * 机盘名
     */
    @Column(name = "disk_name")
    private String diskName;

    private static final long serialVersionUID = 1L;

    /**
     * 机盘类型
     */
    @Column(name = "disk_type")
    private String diskType;

    /**
     * 网元ID
     */
    @Column(name = "net_element_id")
    private Long netElementId;

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
    @Column(name = "amplifier_name")
    private String amplifierName;

    /**
     * 获取机盘ID
     *
     * @return disk_id - 机盘ID
     */
    public Long getDiskId() {
        return diskId;
    }

    /**
     * 设置机盘ID
     *
     * @param diskId 机盘ID
     */
    public void setDiskId(Long diskId) {
        this.diskId = diskId;
    }

    /**
     * @return slot_id
     */
    public Long getSlotId() {
        return slotId;
    }

    /**
     * @param slotId
     */
    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    /**
     * 获取机盘名
     *
     * @return disk_name - 机盘名
     */
    public String getDiskName() {
        return diskName;
    }

    /**
     * 设置机盘名
     *
     * @param diskName 机盘名
     */
    public void setDiskName(String diskName) {
        this.diskName = diskName == null ? null : diskName.trim();
    }

    /**
     * @return amplifier_name
     */
    public String getAmplifierName() {
        return amplifierName;
    }

    /**
     * @param amplifierName
     */
    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName == null ? null : amplifierName.trim();
    }

    /**
     * 获取机盘类型
     *
     * @return disk_type - 机盘类型
     */
    public String getDiskType() {
        return diskType;
    }

    /**
     * 设置机盘类型
     *
     * @param diskType 机盘类型
     */
    public void setDiskType(String diskType) {
        this.diskType = diskType == null ? null : diskType.trim();
    }

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
        ResDisk other = (ResDisk) that;
        return (this.getDiskId() == null ? other.getDiskId() == null : this.getDiskId().equals(other.getDiskId()))
                && (this.getSlotId() == null ? other.getSlotId() == null : this.getSlotId().equals(other.getSlotId()))
                && (this.getDiskName() == null ? other.getDiskName() == null : this.getDiskName().equals(other.getDiskName()))
                && (this.getAmplifierName() == null ? other.getAmplifierName() == null : this.getAmplifierName().equals(other.getAmplifierName()))
                && (this.getDiskType() == null ? other.getDiskType() == null : this.getDiskType().equals(other.getDiskType()))
                && (this.getNetElementId() == null ? other.getNetElementId() == null : this.getNetElementId().equals(other.getNetElementId()))
                && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDiskId() == null) ? 0 : getDiskId().hashCode());
        result = prime * result + ((getSlotId() == null) ? 0 : getSlotId().hashCode());
        result = prime * result + ((getDiskName() == null) ? 0 : getDiskName().hashCode());
        result = prime * result + ((getAmplifierName() == null) ? 0 : getAmplifierName().hashCode());
        result = prime * result + ((getDiskType() == null) ? 0 : getDiskType().hashCode());
        result = prime * result + ((getNetElementId() == null) ? 0 : getNetElementId().hashCode());
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
        sb.append(", diskId=").append(diskId);
        sb.append(", slotId=").append(slotId);
        sb.append(", diskName=").append(diskName);
        sb.append(", amplifierName=").append(amplifierName);
        sb.append(", diskType=").append(diskType);
        sb.append(", netElementId=").append(netElementId);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}