package com.bupt.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "res_disk")
public class ResDisk {
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
}