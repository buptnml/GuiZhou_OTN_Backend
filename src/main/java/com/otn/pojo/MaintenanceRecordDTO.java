package com.otn.pojo;


public class MaintenanceRecordDTO {
    private Long id;
    private Long idNo;
    private String rContent;
    private String deptName;
    private String deptMan;
    private String rPlace;
    private String rType;
    private String repairType;
    private String deviceName;
    private String linkMan;
    private String mobilePhone;
    private String linkWayMobile;
    private String isDone = "0";

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

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptMan() {
        return deptMan;
    }

    public void setDeptMan(String deptMan) {
        this.deptMan = deptMan;
    }

    public String getrPlace() {
        return rPlace;
    }

    public void setrPlace(String rPlace) {
        this.rPlace = rPlace;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
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

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getLinkWayMobile() {
        return linkWayMobile;
    }

    public void setLinkWayMobile(String linkWayMobile) {
        this.linkWayMobile = linkWayMobile;
    }

    public String getIsDone() {
        return isDone.equals("0") ? "未检修":"已检修" ;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }
}
