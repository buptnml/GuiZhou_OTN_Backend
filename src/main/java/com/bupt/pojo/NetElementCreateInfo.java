package com.bupt.pojo;

public class NetElementCreateInfo {
//    coordinateX		number
//    coordinateY		number
//    netElementName		string
//    netElementType		string
    private short coordinateX;
    private short coordinateY;
    private String netElementName;
    private String netElementType;

    public short getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(short coordinateX) {
        this.coordinateX = coordinateX;
    }

    public short getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(short coordinateY) {
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

    public NetElementCreateInfo (short coordinateX , short coordinateY ,String netElementName ,String netElementType){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.netElementName = netElementName;
        this.netElementType = netElementType;
    }

    public NetElementCreateInfo(){
    }

}
