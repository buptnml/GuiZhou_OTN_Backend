package com.otn.pojo;

import java.util.Date;

public class SysSyncLogDTO {
    private Long syncLogId;
    private String operatorName;
    private Date gmtCreate;

    public Long getSyncLogId() {
        return syncLogId;
    }

    public void setSyncLogId(Long syncLogId) {
        this.syncLogId = syncLogId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
