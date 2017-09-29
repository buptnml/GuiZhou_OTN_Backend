package com.bupt.pojo;

import com.bupt.util.tools.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

public class VersionDTO implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Long versionId;
    private String versionName;
    private String versionDictName;
    private String versionDescription;
    //    private VersionSetting versionSetting;
    private String creatorName;
    private Date gmtCreate;
    private Date gmtModified;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

//    public VersionSetting getVersionSetting() {
//        return versionSetting;
//    }
//
//    public void setVersionSetting(VersionSetting versionSetting) {
//        this.versionSetting = versionSetting;
//    }

    public String getVersionDictName() {
        return versionDictName;
    }

    public void setVersionDictName(String versionDictName) {
        this.versionDictName = versionDictName;
    }
}
