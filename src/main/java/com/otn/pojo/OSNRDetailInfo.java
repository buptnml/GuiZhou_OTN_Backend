package com.otn.pojo;

import com.otn.entity.ResBussiness;

import java.text.DecimalFormat;

public class OSNRDetailInfo {
    private String bussinessName;
    private String frequency;
    private String startNetElementName;
    private String endNetElementName;
    private String result;
    private String advice = "";

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public OSNRDetailInfo(ResBussiness bus, Boolean isMain, String startNetElementName, OSNRResult osnrResult) {
        DecimalFormat df = new DecimalFormat("0.0000");
        this.bussinessName = bus.getBussinessName();
        this.frequency = isMain ? bus.getMainFrequency() : bus.getSpareFrequency();
        this.startNetElementName = startNetElementName;
        this.endNetElementName = osnrResult.getNetElementName();
        if(osnrResult.getResult() < 18 ){
            this.result = "OSNR值小于18dB";
            this.advice = "建议：增大"+bussinessName+"光通道的输入功率";
        }
        else{
            this.result = df.format(osnrResult.getResult());
        }
    }

    public OSNRDetailInfo(ResBussiness bus, Boolean isMain, String startNetElementName, String endNetElementName
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
