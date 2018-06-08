package com.otn.pojo;

import com.otn.entity.ResBussiness;
import org.springframework.beans.BeanUtils;

public class OSNRGeneralInfo {
    private Long bussinessId;
    private String bussinessName;
    private String isMain;
    private String realRouteString;
    private String wholeRouteString;
    private String frequency;
    private String isUsable;

    public OSNRGeneralInfo(ResBussiness bus, Boolean isMain, String realRouteString) {
        BeanUtils.copyProperties(bus, this);
        this.isMain = isMain ? "主用" : "备用";
        this.wholeRouteString = isMain ? bus.getMainRoute() : bus.getSpareRoute();
        this.frequency = isMain ? bus.getMainFrequency() : bus.getSpareFrequency();
        this.isUsable = isMain ? bus.getMainRoute().equals(realRouteString) ? "是" : "否"
                : bus.getSpareRoute().equals(realRouteString) ? "是" : "否";
        this.realRouteString = realRouteString;
    }


    public String getRealRouteString() {
        return realRouteString;
    }

    public void setRealRouteString(String realRouteString) {
        this.realRouteString = realRouteString;
    }

    public Long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public String getWholeRouteString() {
        return wholeRouteString;
    }

    public void setWholeRouteString(String wholeRouteString) {
        this.wholeRouteString = wholeRouteString;
    }

    public String getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(String isUsable) {
        this.isUsable = isUsable;
    }
}
