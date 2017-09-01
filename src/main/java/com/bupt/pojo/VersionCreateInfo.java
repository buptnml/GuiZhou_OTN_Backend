package com.bupt.pojo;

public class VersionCreateInfo {
    private String versionName;
    private String baseVersionName;
    private VersionSetting versionSetting;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getBaseVersionName() {
        return baseVersionName;
    }

    public void setBaseVersionName(String baseVersionName) {
        this.baseVersionName = baseVersionName;
    }

    public VersionSetting getVersionSetting() {
        return versionSetting;
    }

    public void setVersionSetting(VersionSetting versionSetting) {
        this.versionSetting = versionSetting;
    }
}
