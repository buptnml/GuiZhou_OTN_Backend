package com.otn.pojo;

import java.util.Date;

public class VersionQuery {
    private String versionName;
    private String versionDictName;
    private String creatorName;
    //    private VersionSetting versionSetting;
    private String versionDescription;


    private Date gmtModified;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = new Date();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDictName() {
        return versionDictName;
    }

    public void setVersionDictName(String versionDictName) {
        this.versionDictName = versionDictName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

//    public VersionSetting getVersionSetting() {
//        return versionSetting;
//    }
//
//    public void setVersionSetting(VersionSetting versionSetting) {
//        this.versionSetting = versionSetting;
//    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }
}
