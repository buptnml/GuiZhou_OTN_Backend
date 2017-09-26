package com.bupt.pojo;

public class DiskCreateInfo {
    private String diskName;
    private String diskType;
    private Long slotId;

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

    public DiskCreateInfo() {
    }

    public DiskCreateInfo(String diskName, String diskType,Long slotId) {
        this.diskName = diskName;
        this.diskType = diskType;
        this.slotId = slotId;
    }
}
