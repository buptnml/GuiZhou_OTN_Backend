package com.otn.pojo;

public class SyncResultDTO {
    private int netElementChange = Integer.MIN_VALUE;
    private int diskChange = Integer.MIN_VALUE;
    private int businessChange = Integer.MIN_VALUE;
    private int linkChange = Integer.MIN_VALUE;


    public int getNetElementChange() {
        return netElementChange;
    }

    public void setNetElementChange(int netElementChange) {
        this.netElementChange = netElementChange;
    }

    public int getDiskChange() {
        return diskChange;
    }

    public void setDiskChange(int diskChange) {
        this.diskChange = diskChange;
    }

    public int getBusinessChange() {
        return businessChange;
    }

    public void setBusinessChange(int businessChange) {
        this.businessChange = businessChange;
    }

    public int getLinkChange() {
        return linkChange;
    }

    public void setLinkChange(int linkChange) {
        this.linkChange = linkChange;
    }
}
