package com.bupt.pojo;

import com.bupt.entity.*;

import java.util.List;

public class SysVersionBackUpInfo {
    private Long versionId;
    private List<ResBussiness> bussinessBackUp;
    private List<ResDisk> diskBackUp;
    private List<ResLink> linkBackUp;
    private List<ResNetElement> netElementBackUp;
    private List<ResOnsrLinkType> osnrLinkTypeBackUp;
    private List<ResOsnrAmplifier> osnrAmplifierBackUp;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public List<ResBussiness> getBussinessBackUp() {
        return bussinessBackUp;
    }

    public void setBussinessBackUp(List<ResBussiness> bussinessBackUp) {
        this.bussinessBackUp = bussinessBackUp;
    }

    public List<ResDisk> getDiskBackUp() {
        return diskBackUp;
    }

    public void setDiskBackUp(List<ResDisk> diskBackUp) {
        this.diskBackUp = diskBackUp;
    }

    public List<ResLink> getLinkBackUp() {
        return linkBackUp;
    }

    public void setLinkBackUp(List<ResLink> linkBackUp) {
        this.linkBackUp = linkBackUp;
    }

    public List<ResNetElement> getNetElementBackUp() {
        return netElementBackUp;
    }

    public void setNetElementBackUp(List<ResNetElement> netElementBackUp) {
        this.netElementBackUp = netElementBackUp;
    }

    public List<ResOnsrLinkType> getOsnrLinkTypeBackUp() {
        return osnrLinkTypeBackUp;
    }

    public void setOsnrLinkTypeBackUp(List<ResOnsrLinkType> osnrLinkTypeBackUp) {
        this.osnrLinkTypeBackUp = osnrLinkTypeBackUp;
    }

    public List<ResOsnrAmplifier> getOsnrAmplifierBackUp() {
        return osnrAmplifierBackUp;
    }

    public void setOsnrAmplifierBackUp(List<ResOsnrAmplifier> osnrAmplifierBackUp) {
        this.osnrAmplifierBackUp = osnrAmplifierBackUp;
    }
}
