package com.bupt.pojo;

import com.bupt.entity.ResBussiness;

import java.text.DecimalFormat;

public class ResultOSNRDetail {
    private String bussinessName;
    private String frequency;
    private String startNetElementName;
    private String endNetElementName;
    private String result;

    public ResultOSNRDetail(ResBussiness bus, Boolean isMain, String startNetElementName, OSNRResult osnrResult) {
        DecimalFormat df = new DecimalFormat("0.0000");
        this.bussinessName = bus.getBussinessName();
        this.frequency = isMain ? bus.getMainFrequency() : bus.getSpareFrequency();
        this.startNetElementName = startNetElementName;
        this.endNetElementName = osnrResult.getNetElementName();
        this.result = osnrResult.getResult() < 18 ? "OSNR值小于18dB" : df.format(osnrResult.getResult());
    }

    public ResultOSNRDetail(ResBussiness bus, Boolean isMain, String startNetElementName, String endNetElementName
            , String errorMessage) {
        this.bussinessName = bus.getBussinessName();
        this.frequency = isMain ? bus.getMainFrequency() : bus.getSpareFrequency();
        this.startNetElementName = startNetElementName;
        this.endNetElementName = endNetElementName;
        this.result = errorMessage;
    }


    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStartNetElementName() {
        return startNetElementName;
    }

    public void setStartNetElementName(String startNetElementName) {
        this.startNetElementName = startNetElementName;
    }

    public String getEndNetElementName() {
        return endNetElementName;
    }

    public void setEndNetElementName(String endNetElementName) {
        this.endNetElementName = endNetElementName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
