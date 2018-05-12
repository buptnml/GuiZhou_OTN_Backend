package com.otn.pojo;

import org.springframework.lang.Nullable;

public class BussinessCreateInfo {
    private String bussinessName;
    private String bussinessRate;
    private String mainRoute;
    private String mainFrequency;
    @Nullable
    private String spareRoute;
    @Nullable
    private String spareFrequency;
    private Double inputPower;
    @Nullable
    private String circleId;

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBussinessRate() {
        return bussinessRate;
    }

    public void setBussinessRate(String bussinessRate) {
        this.bussinessRate = bussinessRate;
    }

    public String getMainRoute() {
        return mainRoute;
    }

    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute;
    }

    public String getMainFrequency() {
        return mainFrequency;
    }

    public void setMainFrequency(String mainFrequency) {
        this.mainFrequency = mainFrequency;
    }

    @Nullable
    public String getSpareRoute() {
        return spareRoute;
    }

    public void setSpareRoute(@Nullable String spareRoute) {
        this.spareRoute = spareRoute;
    }

    @Nullable
    public String getSpareFrequency() {
        return spareFrequency;
    }

    public void setSpareFrequency(@Nullable String spareFrequency) {
        if (null == spareFrequency) {
            this.spareFrequency = (int) (100 * Math.random()) + "";
            return;
        }
        this.spareFrequency = spareFrequency;
    }

    public Double getInputPower() {
        return inputPower;
    }

    public void setInputPower(Double inputPower) {
        this.inputPower = inputPower;
    }

    @Nullable
    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(@Nullable String circleId) {
        this.circleId = circleId;
    }
}
