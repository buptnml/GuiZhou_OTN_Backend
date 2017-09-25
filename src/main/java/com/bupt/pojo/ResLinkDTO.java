package com.bupt.pojo;

public class ResLinkDTO {
    private Long linkId;
    private String linkName;
    private String linkType;
    private Float linkLength;
    private Float linkLoss;
    private Long endAId;
    private String endAName;
    private Long endZId;
    private String endZName;

    public Float getLinkLength() {
        return linkLength;
    }

    public void setLinkLength(Float linkLength) {
        this.linkLength = linkLength;
    }

    public Float getLinkLoss() {
        return linkLoss;
    }

    public void setLinkLoss(Float linkLoss) {
        this.linkLoss = linkLoss;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Long getEndAId() {
        return endAId;
    }

    public void setEndAId(Long endAId) {
        this.endAId = endAId;
    }

    public String getEndAName() {
        return endAName;
    }

    public void setEndAName(String endAName) {
        this.endAName = endAName;
    }

    public Long getEndZId() {
        return endZId;
    }

    public void setEndZId(Long endZId) {
        this.endZId = endZId;
    }

    public String getEndZName() {
        return endZName;
    }

    public void setEndZName(String endZName) {
        this.endZName = endZName;
    }

}
