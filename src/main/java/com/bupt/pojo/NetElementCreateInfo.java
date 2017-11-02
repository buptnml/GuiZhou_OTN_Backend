package com.bupt.pojo;

public class NetElementCreateInfo {
    private Float coordinateX;
    private Float coordinateY;
    private String netElementName;
    private String netElementType;

    public NetElementCreateInfo(Float coordinateX, Float coordinateY, String netElementName, String netElementType) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.netElementName = netElementName;
        this.netElementType = netElementType;
    }

    public NetElementCreateInfo() {
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

    public String getNetElementName() {
        return netElementName;
    }

    public void setNetElementName(String netElementName) {
        this.netElementName = netElementName;
    }

    public String getNetElementType() {
        return netElementType;
    }

    public void setNetElementType(String netElementType) {
        this.netElementType = netElementType;
    }
}
