package com.bupt.entity;

public class DiskCreateInfo {
    private String diskName;
    private String diskType;

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public DiskCreateInfo() {
    }

    public DiskCreateInfo(String diskName, String diskType) {
        this.diskName = diskName;
        this.diskType = diskType;
    }
}
