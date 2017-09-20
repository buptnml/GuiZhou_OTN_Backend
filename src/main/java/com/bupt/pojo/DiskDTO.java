package com.bupt.pojo;

public class DiskDTO {
    private Long diskId;
    private String diskName;
    private String diskType;
    private Long netElementId;

    public Long getDiskId() {
        return diskId;
    }

    public void setDiskId(Long diskId) {
        this.diskId = diskId;
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

    public Long getNetElementId() {
        return netElementId;
    }

    public void setNetElementId(Long netElementId) {
        this.netElementId = netElementId;
    }
}
