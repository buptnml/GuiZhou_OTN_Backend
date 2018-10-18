package com.otn.pojo;

public class RecordCreateQuery {
    private String target;
    private String interruptBus;
    private String affectBus;


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInterruptBus() {
        return interruptBus;
    }

    public void setInterruptBus(String interruptBus) {
        this.interruptBus = interruptBus;
    }

    public String getAffectBus() {
        return affectBus;
    }

    public void setAffectBus(String affectBus) {
        this.affectBus = affectBus;
    }
}
