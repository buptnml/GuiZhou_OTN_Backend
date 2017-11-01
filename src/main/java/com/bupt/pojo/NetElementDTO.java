package com.bupt.pojo;

public class NetElementDTO {
    private Long netElementId;
    private String netElementName;
    private String netElementType;
    private Float coordinateX;
    private Float coordinateY;

    public NetElementDTO(Long netElementId, String netElementName, String netElementType, Float coordinateX, Float coordinateY) {
        this.netElementId = netElementId;
        this.netElementName = netElementName;
        this.netElementType = netElementType;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public NetElementDTO() {

    }

    public Long getNetElementId() {
        return netElementId;
    }

    public void setNetElementId(Long netElementId) {
        this.netElementId = netElementId;
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

    @Override
    public String toString() {
        return "NetElementDTO{" +
                "netElementId=" + netElementId +
                ", netElementName='" + netElementName + '\'' +
                ", netElementType='" + netElementType + '\'' +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                '}';
    }
}
