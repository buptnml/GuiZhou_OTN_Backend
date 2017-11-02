package com.bupt.pojo;

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
        this.spareFrequency = spareFrequency;
    }

    public Double getInputPower() {
        return inputPower;
    }

    public void setInputPower(Double inputPower) {
        this.inputPower = inputPower;
    }
}
