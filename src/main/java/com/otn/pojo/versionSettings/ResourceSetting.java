package com.otn.pojo.versionSettings;

import java.io.Serializable;

public class ResourceSetting implements Serializable {

    private static final long serialVersionUID = 1L;


    private boolean bussiness = false;//光通道表
    private boolean netElement = false;//网元表
    private boolean disk = false;//机盘表
    private boolean link = false;//链路表
    private boolean linkType = false;//链路类型表
    private boolean amplifier = false;//放大器表


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isBussiness() {
        return bussiness;
    }

    public void setBussiness(boolean bussiness) {
        this.bussiness = bussiness;
    }

    public boolean isNetElement() {
        return netElement;
    }

    public void setNetElement(boolean netElement) {
        this.netElement = netElement;
    }

    public boolean isDisk() {
        return disk;
    }

    public void setDisk(boolean disk) {
        this.disk = disk;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean isLinkType() {
        return linkType;
    }

    public void setLinkType(boolean linkType) {
        this.linkType = linkType;
    }

    public boolean isAmplifier() {
        return amplifier;
    }

    public void setAmplifier(boolean amplifier) {
        this.amplifier = amplifier;
    }
}
