package com.bupt.facade.OSNRCalculator;

public class RouteOSNRDetail {
    private String bussinessName;
    private String isMain;
    private String routeString;
    private String isUsable;

    public RouteOSNRDetail(String bussinessName, Boolean isMain, String routeString, String isUsable) {
        this.bussinessName = bussinessName;
        this.isMain = isMain ? "是" : "否";
        this.routeString = routeString;
        this.isUsable = isUsable;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getRouteString() {
        return routeString;
    }

    public void setRouteString(String routeString) {
        this.routeString = routeString;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }
}
