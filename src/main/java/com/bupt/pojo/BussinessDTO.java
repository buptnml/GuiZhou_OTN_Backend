package com.bupt.pojo;

public class BussinessDTO {
    private Long bussinessId;
    private String bussinessName;
    private String bussinessRate;
    private String mainRoute;
    private String mainFrequency;
    private String spareRoute;
    private String spareFrequency;


    public Long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

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

    public String getSpareRoute() {
        return spareRoute;
    }

    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute;
    }

    public String getSpareFrequency() {
        return spareFrequency;
    }

    public void setSpareFrequency(String spareFrequency) {
        this.spareFrequency = spareFrequency;
    }
}
