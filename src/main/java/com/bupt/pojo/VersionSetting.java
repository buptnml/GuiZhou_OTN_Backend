package com.bupt.pojo;

/**
 * 版本配置信息
 */
public class VersionSetting {
    private boolean bussiness = false;//光通道表
    private boolean netElement = false;//网元表
    private boolean disk = false;//机盘表
    private boolean link = false;//链路表
    private boolean linkType = false;//链路类型表
    private boolean amplifier = false;//放大器表

//    private BussinessDisplaySetting bussinessDisplaySetting;//光通道表显示设置
//    private NetElementDisplaySetting netElementDisplaySetting;//网元表显示设置
//    private DiskDisplaySetting diskDisplaySetting;//机盘表显示设置
//    private LinkDisplaySetting linkDisplaySetting;//链接表显示设置
//    private LinkTypeDisplaySetting linkTypeDisplaySetting;//链路类型表显示设置
//    private AmplifierDisplaySetting amplifierDisplaySetting;//放大器显示设置

    public boolean hasBussiness() {
        return bussiness;
    }

    public void setBussiness(boolean bussiness) {
        this.bussiness = bussiness;
    }

    public boolean hasNetElement() {
        return netElement;
    }

    public void setNetElement(boolean netElement) {
        this.netElement = netElement;
    }

    public boolean hasDisk() {
        return disk;
    }

    public void setDisk(boolean disk) {
        this.disk = disk;
    }

    public boolean hasLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean hasLinkType() {
        return linkType;
    }

    public void setLinkType(boolean linkType) {
        this.linkType = linkType;
    }

    public boolean hasAmplifier() {
        return amplifier;
    }

    public void setAmplifier(boolean amplifier) {
        this.amplifier = amplifier;
    }
}
