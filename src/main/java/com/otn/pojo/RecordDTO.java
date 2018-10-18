package com.otn.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otn.util.tools.CustomDateSerializer;

import java.util.Date;

public class RecordDTO {
    private Long recordId;
    private String target;
    private String interruptBus;
    private String affectBus;
    private Date gmtCreate;
    private Long versionId;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

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

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
