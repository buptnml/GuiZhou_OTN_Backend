package com.otn.webservice.com.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class RawLinkData {


    @XmlElement(name = "link_name")
    private String linkName;
    @XmlElement(name = "equip_a_id")
    private Long endAId;
    @XmlElement(name = "equip_a_name")
    private String endAName;
    @XmlElement(name = "equip_z_id")
    private Long endZId;
    @XmlElement(name = "equip_z_name")
    private String endZName;
    @XmlElement(name = "link_type")
    private String linkType;
    @XmlElement(name = "link_length")
    private Float linkLength;

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Float getLinkLength() {
        return linkLength;
    }

    public void setLinkLength(Float linkLength) {
        this.linkLength = linkLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawLinkData)) return false;
        RawLinkData that = (RawLinkData) o;
        return (getEndAId().equals(that.getEndAId()) && getEndZId().equals(that.getEndZId())) ||
                (getEndAId().equals(that.getEndZId()) && getEndZId().equals(that.getEndAId()));
    }

    @Override
    public int hashCode() {
        return (getEndAId() != null ? getEndAId().hashCode() : 0) + (getEndZId() != null ? getEndZId().hashCode() : 0);

    }
}
