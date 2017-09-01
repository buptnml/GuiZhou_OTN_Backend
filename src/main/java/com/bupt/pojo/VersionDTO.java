package com.bupt.pojo;

public class VersionDTO {
    private Long versionId;
    private String versionName;
    private VersionSetting versionSetting;

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

    public VersionSetting getVersionSetting() {
        return versionSetting;
    }

    public void setVersionSetting(VersionSetting versionSetting) {
        this.versionSetting = versionSetting;
    }
}
