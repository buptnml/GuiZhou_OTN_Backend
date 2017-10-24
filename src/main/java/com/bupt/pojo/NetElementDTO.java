package com.bupt.pojo;

public class NetElementDTO {
    private Long netElementId;
    private String netElementName;
    private String netElementType;
    private Short coordinateX;
    private Short coordinateY;

    public NetElementDTO(Long id, String netElementName, String netElementType, Short coordinateX, Short coordinateY) {
        this.netElementId = id;
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

    public Short getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Short coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Short getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Short coordinateY) {
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
