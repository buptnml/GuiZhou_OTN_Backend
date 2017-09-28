package com.bupt.pojo;

/**
 * Created by caoxiaohong on 17/9/20.
 */
public class LinkTypeDTO{

    Float   linkLoss;//链路损耗，单位dB/km
    String  linkRate;//链路速率
    String  linkType;//链路类型
    Long    linkTypeId;//链路类型ID
    //Long    versionId;//条目所在版本ID


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

    public Long getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(Long linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

//    public Long getVersionId() {
//        return versionId;
//    }
//
//    public void setVersionId(Long versionId) {
//        this.versionId = versionId;
//    }


    @Override
    public String toString() {
        return "LinkTypeDTO{" +
                "linkLoss=" + linkLoss +
                ", linkRate='" + linkRate + '\'' +
                ", linkType='" + linkType + '\'' +
                ", linkTypeId=" + linkTypeId +
                '}';
    }
}
