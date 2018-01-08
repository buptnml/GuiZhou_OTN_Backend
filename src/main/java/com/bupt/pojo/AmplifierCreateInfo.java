package com.bupt.pojo;

public class AmplifierCreateInfo {
    private String amplifierName;
    private String diskType;
    private Short gain;
    private Short minimumInputPower;
    private Short maximumInputPower;
    private Short maximumOutputPower;

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public String getAmplifierName() {
        return amplifierName;
    }

    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName;
    }

    public Short getGain() {
        return gain;
    }

    public void setGain(Short gain) {
        this.gain = gain;
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

    public Short getMaximumOutputPower() {
        return maximumOutputPower;
    }

    public void setMaximumOutputPower(Short maximumOutputPower) {
        this.maximumOutputPower = maximumOutputPower;
    }
}
