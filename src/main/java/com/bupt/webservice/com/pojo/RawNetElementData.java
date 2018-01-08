package com.bupt.webservice.com.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class RawNetElementData {
    @XmlElement(name = "equip_id")
    private Long netElementId;
    @XmlElement(name = "equip_name")
    private String netElementName;
    @XmlElement(name = "location_x")
    private Float coordinateX;
    @XmlElement(name = "location_y")
    private Float coordinateY;
    @XmlElement(name = "pack_type")
    private String amplifierNames;


    public Long getNetElementId() {
        return netElementId;
    }

    public void setNetElementId(Long netElementId) {
        this.netElementId = netElementId;
    }

    public String getNetElementName() {
        return netElementName;
    }

    public void setNetElementName(String netElementName) {
        this.netElementName = netElementName;
    }

    public Float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getAmplifierNames() {
        return amplifierNames;
    }

    public void setAmplifierNames(String amplifierNames) {
        this.amplifierNames = amplifierNames;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawNetElementData)) return false;
        RawNetElementData that = (RawNetElementData) o;
        if (getNetElementName() != null ? getNetElementName().equals(that.getNetElementName()) : that
                .getNetElementName() == null) {
            return true;
        } else {
            if (getNetElementId() != null ? !getNetElementId().equals(that.getNetElementId()) : that
                    .getNetElementId() != null)
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (getNetElementName() != null ? getNetElementName().hashCode() : 0);

    }
}

