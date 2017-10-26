package com.bupt.pojo;

public class NetElementCreateInfo {
    private short coordinateX;
    private short coordinateY;
    private String netElementName;
    private String netElementType;

    public NetElementCreateInfo(short coordinateX, short coordinateY, String netElementName, String netElementType) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.netElementName = netElementName;
        this.netElementType = netElementType;
    }

    public NetElementCreateInfo() {
    }

    public short getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = (short) coordinateX;
    }

    public short getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = (short) coordinateY;
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
