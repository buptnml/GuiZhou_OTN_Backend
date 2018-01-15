package com.pojo;

public class DiskCreateInfo {
    private String diskName;
    private String diskType;
    private String amplifierName;
    private Long slotId;

    public DiskCreateInfo() {
    }


    public DiskCreateInfo(String diskName, String diskType, String amplifierName, Long slotId) {
        this.diskName = diskName;
        this.diskType = diskType;
        this.amplifierName = amplifierName;
        this.slotId = slotId;
    }

    public String getAmplifierName() {
        return amplifierName;
    }

    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

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
}
