package com.otn.pojo;

import org.springframework.lang.Nullable;

public class MaintenanceRecordDTO {
    private Long id;
    private Long idNo;
    @Nullable
    private String deptName;
    @Nullable
    private String deptMan;
    @Nullable
    private String rPlace;
    @Nullable
    private String rType;
    private String repairType;
    private String deviceName;
    @Nullable
    private String rContent;
    @Nullable
    private String linkMan;
    @Nullable
    private String mobilePhone;
    @Nullable
    private String linkWayMobile;

    private String isDone;

    public void setIsDone(String isDone) {
        this.isDone = isDone.equals("0") ? "未检修" : "已检修";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNo() {
        return idNo;
    }

    public void setIdNo(Long idNo) {
        this.idNo = idNo;
    }

    @Nullable
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(@Nullable String deptName) {
        this.deptName = deptName;
    }

    @Nullable
    public String getDeptMan() {
        return deptMan;
    }

    public void setDeptMan(@Nullable String deptMan) {
        this.deptMan = deptMan;
    }

    @Nullable
    public String getRPlace() {
        return rPlace;
    }

    public void setRPlace(@Nullable String rPlace) {
        this.rPlace = rPlace;
    }

    @Nullable
    public String getRType() {
        return rType;
    }

    public void setRType(@Nullable String rType) {
        this.rType = rType;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Nullable
    public String getRContent() {
        return rContent;
    }

    public void setRContent(@Nullable String rContent) {
        this.rContent = rContent;
    }

    @Nullable
    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(@Nullable String linkMan) {
        this.linkMan = linkMan;
    }

    @Nullable
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(@Nullable String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Nullable
    public String getLinkWayMobile() {
        return linkWayMobile;
    }

    public void setLinkWayMobile(@Nullable String linkWayMobile) {
        this.linkWayMobile = linkWayMobile;
    }
}
