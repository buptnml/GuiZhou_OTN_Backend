package com.bupt.pojo;

public class LinkTypeCreateInfo {
    private Float   linkLoss;//链路损耗，单位dB/km
    private String  linkRate;//链路速率
    private String  linkType;//链路类型

    public Float getLinkLoss() {
        return linkLoss;
    }

    public void setLinkLoss(Float linkLoss) {
        this.linkLoss = linkLoss;
    }

    public String getLinkRate() {
        return linkRate;
    }

    public void setLinkRate(String linkRate) {
        this.linkRate = linkRate;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
}
