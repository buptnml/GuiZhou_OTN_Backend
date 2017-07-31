package com.bupt.pojo;

import java.util.Date;

/**
 * Created by 韩宪斌 on 2017/7/13.
 */
public class VersionDTO {
    private Long versionId;
    private String versionName;
    private String versionDescription;
    private Date gmtCreate;
    private Date gmtModified;
    
    public Long getVersionId() {
        return versionId;
    }
    
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
    
    public String getVersionName() {
        return versionName;
    }
    
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    
    public String getVersionDescription() {
        return versionDescription;
    }
    
    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }
    
    public Date getGmtCreate() {
        return gmtCreate;
    }
    
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
    public Date getGmtModified() {
        return gmtModified;
    }
    
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
    
    public VersionDTO() {
    }
}
