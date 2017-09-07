package com.bupt.pojo;

import com.bupt.util.exception.controller.input.NullArgumentException;

public class BussinessCreateInfo {
    String bussinessName;
    String mainRoute;
    String spareRoute;
    OsnrChannelCreateInfo mainChannelInfo;
    OsnrChannelCreateInfo spareChannelInfo;

    public void checkBussinessCreateInfoLegal(){
        if(mainRoute.equals("")){
            throw new IllegalArgumentException("mainRoute");
        }
        if(null == this.getBussinessName()){
            throw new NullArgumentException("bussinessName");
        }
        if(null == this.getMainRoute()){
            throw  new NullArgumentException("mainRoute");
        }
        if(null == this.getMainChannelInfo()){
            throw new NullArgumentException("mainRoute");
        }else{
            mainChannelInfo.checkOsnrChannelCreateInfo();
        }
        if(null != this.getSpareRoute()){
            if(spareRoute.equals("")){
                throw new IllegalArgumentException("spareRoute");
            }
            if(null == this.getSpareChannelInfo()){
                throw new NullArgumentException("spareChannelInfo");
            }else{
                spareChannelInfo.checkOsnrChannelCreateInfo();
            }
        }
    }


    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getMainRoute() {
        return mainRoute;
    }

    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute;
    }

    public String getSpareRoute() {
        return spareRoute;
    }

    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute;
    }

    public OsnrChannelCreateInfo getMainChannelInfo() {
        return mainChannelInfo;
    }

    public void setMainChannelInfo(OsnrChannelCreateInfo mainChannelInfo) {
        this.mainChannelInfo = mainChannelInfo;
    }

    public OsnrChannelCreateInfo getSpareChannelInfo() {
        return spareChannelInfo;
    }

    public void setSpareChannelInfo(OsnrChannelCreateInfo spareChannelInfo) {
        this.spareChannelInfo = spareChannelInfo;
    }
}
