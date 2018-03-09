package com.otn.pojo;

public class SyncResultDTO {
    private String netElementChange;
    private String diskChange;
    private String businessChange;
    private String linkChange;

    public String getNetElementChange() {
        return netElementChange;
    }

    public void setNetElementChange(int res) {
        this.netElementChange = res == 0 ? "无变化" : (res + "");
    }

    public String getDiskChange() {
        return diskChange;
    }

    public void setDiskChange(int res) {
        this.diskChange = res == 0 ? "无变化" : (res + "");
    }

    public String getBusinessChange() {
        return businessChange;
    }

    public void setBusinessChange(int res) {
        this.businessChange = res == 0 ? "无变化" : (res + "");
    }

    public String getLinkChange() {
        return linkChange;
    }

    public void setLinkChange(int res) {
        this.linkChange = res == 0 ? "无变化" : (res + "");
    }
}
