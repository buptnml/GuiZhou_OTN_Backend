package com.bupt.pojo;

import org.springframework.lang.Nullable;

public class OSNRLegalCheckRequest {
    private String routeString;
    @Nullable
    private Double inputPower;

    public String getRouteString() {
        return routeString;
    }

    public void setRouteString(String routeString) {
        this.routeString = routeString;
    }

    @Nullable
    public Double getInputPower() {
        return inputPower;
    }

    public void setInputPower(@Nullable Double inputPower) {
        this.inputPower = inputPower;
    }
}
