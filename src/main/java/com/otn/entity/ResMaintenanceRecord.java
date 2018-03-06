package com.otn.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "res_maintenance_record")
public class ResMaintenanceRecord implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "maintenance_record_id")
    private Long maintenanceRecordId;

    /**
     * 检修票据编号
     */
    @Column(name = "maintenance_record_sub_id")
    private Long maintenanceRecordSubId;

    /**
     * 申请单位
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 申请人
     */
    @Column(name = "dept_man")
    private String deptMan;

    /**
     * 工作地点
     */
    @Column(name = "r_place")
    private String rPlace;

    /**
     * 检修类别
     */
    @Column(name = "r_type")
    private String rType;

    /**
     * 检修单类型
     */
    @Column(name = "repair_type")
    private String repairType;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 工作内容
     */
    @Column(name = "r_content")
    private String rContent;

    /**
     * 联系人
     */
    @Column(name = "link_man")
    private String linkMan;

    /**
     * 移动电话
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 工作移动电话
     */
    @Column(name = "link_way_mobile")
    private String linkWayMobile;

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
     * 获取主键
     *
     * @return maintenance_record_id - 主键
     */
    public Long getMaintenanceRecordId() {
        return maintenanceRecordId;
    }

    /**
     * 设置主键
     *
     * @param maintenanceRecordId 主键
     */
    public void setMaintenanceRecordId(Long maintenanceRecordId) {
        this.maintenanceRecordId = maintenanceRecordId;
    }

    /**
     * 获取检修票据编号
     *
     * @return maintenance_record_sub_id - 检修票据编号
     */
    public Long getMaintenanceRecordSubId() {
        return maintenanceRecordSubId;
    }

    /**
     * 设置检修票据编号
     *
     * @param maintenanceRecordSubId 检修票据编号
     */
    public void setMaintenanceRecordSubId(Long maintenanceRecordSubId) {
        this.maintenanceRecordSubId = maintenanceRecordSubId;
    }

    /**
     * 获取申请单位
     *
     * @return dept_name - 申请单位
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置申请单位
     *
     * @param deptName 申请单位
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * 获取申请人
     *
     * @return dept_man - 申请人
     */
    public String getDeptMan() {
        return deptMan;
    }

    /**
     * 设置申请人
     *
     * @param deptMan 申请人
     */
    public void setDeptMan(String deptMan) {
        this.deptMan = deptMan == null ? null : deptMan.trim();
    }

    /**
     * 获取工作地点
     *
     * @return r_place - 工作地点
     */
    public String getrPlace() {
        return rPlace;
    }

    /**
     * 设置工作地点
     *
     * @param rPlace 工作地点
     */
    public void setrPlace(String rPlace) {
        this.rPlace = rPlace == null ? null : rPlace.trim();
    }

    /**
     * 获取检修类别
     *
     * @return r_type - 检修类别
     */
    public String getrType() {
        return rType;
    }

    /**
     * 设置检修类别
     *
     * @param rType 检修类别
     */
    public void setrType(String rType) {
        this.rType = rType == null ? null : rType.trim();
    }

    /**
     * 获取检修单类型
     *
     * @return repair_type - 检修单类型
     */
    public String getRepairType() {
        return repairType;
    }

    /**
     * 设置检修单类型
     *
     * @param repairType 检修单类型
     */
    public void setRepairType(String repairType) {
        this.repairType = repairType == null ? null : repairType.trim();
    }

    /**
     * 获取设备名称
     *
     * @return device_name - 设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名称
     *
     * @param deviceName 设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * 获取工作内容
     *
     * @return r_content - 工作内容
     */
    public String getrContent() {
        return rContent;
    }

    /**
     * 设置工作内容
     *
     * @param rContent 工作内容
     */
    public void setrContent(String rContent) {
        this.rContent = rContent == null ? null : rContent.trim();
    }

    /**
     * 获取联系人
     *
     * @return link_man - 联系人
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * 设置联系人
     *
     * @param linkMan 联系人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    /**
     * 获取移动电话
     *
     * @return mobile_phone - 移动电话
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置移动电话
     *
     * @param mobilePhone 移动电话
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    /**
     * 获取工作移动电话
     *
     * @return link_way_mobile - 工作移动电话
     */
    public String getLinkWayMobile() {
        return linkWayMobile;
    }

    /**
     * 设置工作移动电话
     *
     * @param linkWayMobile 工作移动电话
     */
    public void setLinkWayMobile(String linkWayMobile) {
        this.linkWayMobile = linkWayMobile == null ? null : linkWayMobile.trim();
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
        ResMaintenanceRecord other = (ResMaintenanceRecord) that;
        return (this.getMaintenanceRecordId() == null ? other.getMaintenanceRecordId() == null : this.getMaintenanceRecordId().equals(other.getMaintenanceRecordId()))
                && (this.getMaintenanceRecordSubId() == null ? other.getMaintenanceRecordSubId() == null : this.getMaintenanceRecordSubId().equals(other.getMaintenanceRecordSubId()))
                && (this.getDeptName() == null ? other.getDeptName() == null : this.getDeptName().equals(other.getDeptName()))
                && (this.getDeptMan() == null ? other.getDeptMan() == null : this.getDeptMan().equals(other.getDeptMan()))
                && (this.getrPlace() == null ? other.getrPlace() == null : this.getrPlace().equals(other.getrPlace()))
                && (this.getrType() == null ? other.getrType() == null : this.getrType().equals(other.getrType()))
                && (this.getRepairType() == null ? other.getRepairType() == null : this.getRepairType().equals(other.getRepairType()))
                && (this.getDeviceName() == null ? other.getDeviceName() == null : this.getDeviceName().equals(other.getDeviceName()))
                && (this.getrContent() == null ? other.getrContent() == null : this.getrContent().equals(other.getrContent()))
                && (this.getLinkMan() == null ? other.getLinkMan() == null : this.getLinkMan().equals(other.getLinkMan()))
                && (this.getMobilePhone() == null ? other.getMobilePhone() == null : this.getMobilePhone().equals(other.getMobilePhone()))
                && (this.getLinkWayMobile() == null ? other.getLinkWayMobile() == null : this.getLinkWayMobile().equals(other.getLinkWayMobile()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaintenanceRecordId() == null) ? 0 : getMaintenanceRecordId().hashCode());
        result = prime * result + ((getMaintenanceRecordSubId() == null) ? 0 : getMaintenanceRecordSubId().hashCode());
        result = prime * result + ((getDeptName() == null) ? 0 : getDeptName().hashCode());
        result = prime * result + ((getDeptMan() == null) ? 0 : getDeptMan().hashCode());
        result = prime * result + ((getrPlace() == null) ? 0 : getrPlace().hashCode());
        result = prime * result + ((getrType() == null) ? 0 : getrType().hashCode());
        result = prime * result + ((getRepairType() == null) ? 0 : getRepairType().hashCode());
        result = prime * result + ((getDeviceName() == null) ? 0 : getDeviceName().hashCode());
        result = prime * result + ((getrContent() == null) ? 0 : getrContent().hashCode());
        result = prime * result + ((getLinkMan() == null) ? 0 : getLinkMan().hashCode());
        result = prime * result + ((getMobilePhone() == null) ? 0 : getMobilePhone().hashCode());
        result = prime * result + ((getLinkWayMobile() == null) ? 0 : getLinkWayMobile().hashCode());
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
        sb.append(", maintenanceRecordId=").append(maintenanceRecordId);
        sb.append(", maintenanceRecordSubId=").append(maintenanceRecordSubId);
        sb.append(", deptName=").append(deptName);
        sb.append(", deptMan=").append(deptMan);
        sb.append(", rPlace=").append(rPlace);
        sb.append(", rType=").append(rType);
        sb.append(", repairType=").append(repairType);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", rContent=").append(rContent);
        sb.append(", linkMan=").append(linkMan);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", linkWayMobile=").append(linkWayMobile);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}