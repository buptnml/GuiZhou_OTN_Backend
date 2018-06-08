package com.otn.webservice.com.pojo;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class RawAmpData {
    @XmlElement(name = "equip_id")
    private Long netElementId;
    @XmlElement(name = "equip_name")
    private String diskName;
    @XmlElement(name = "card_model")
    private String amplifierName;
    @XmlElement(name = "card_type")
    private String diskType;
    @XmlElement(name = "min")
    private Short minimumGain;
    @XmlElement(name = "max")
    private Short maximumGain;
    @XmlElement(name = "min_in_opl")
    private Short minimumInputPower;
    @XmlElement(name = "max_in_opl")
    private Short maximumInputPower;
    @XmlElement(name = "min_out_opl")
    private Short minimumOutputPower;
    @XmlElement(name = "max_out_opl")
    private Short maximumOutputPower;


    public Long getNetElementId() {
        return netElementId;
    }

    public void setNetElementId(Long netElementId) {
        this.netElementId = netElementId;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public Short getGain() {
        return (short) ((minimumGain + maximumGain) / 2);
    }

    public String getAmplifierName() {
        return amplifierName;
    }

    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public Short getMinimumGain() {
        return minimumGain;
    }

    public void setMinimumGain(Short minimumGain) {
        this.minimumGain = minimumGain;
    }

    public Short getMaximumGain() {
        return maximumGain;
    }

    public void setMaximumGain(Short maximumGain) {
        this.maximumGain = maximumGain;
    }

    public Short getMinimumInputPower() {
        return minimumInputPower;
    }

    public void setMinimumInputPower(Short minimumInputPower) {
        this.minimumInputPower = minimumInputPower;
    }

    public Short getMaximumInputPower() {
        return maximumInputPower;
    }

    public void setMaximumInputPower(Short maximumInputPower) {
        this.maximumInputPower = maximumInputPower;
    }

    public Short getMinimumOutputPower() {
        return minimumOutputPower;
    }

    public void setMinimumOutputPower(Short minimumOutputPower) {
        this.minimumOutputPower = minimumOutputPower;
    }

    public Short getMaximumOutputPower() {
        return maximumOutputPower;
    }

    public void setMaximumOutputPower(Short maximumOutputPower) {
        this.maximumOutputPower = maximumOutputPower;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawAmpData)) return false;

        RawAmpData that = (RawAmpData) o;

        return getNetElementId() != null ? getNetElementId().equals(that.getNetElementId()) : that.getNetElementId() == null;
    }

    @Override
    public int hashCode() {
        return getNetElementId() != null ? getNetElementId().hashCode() : 0;
    }
}
