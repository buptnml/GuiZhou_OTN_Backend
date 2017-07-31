package com.bupt.pojo;

/**
 * Created by 韩宪斌 on 2017/7/13.
 */
public class VersionCreateInfo {
    private Long userId;
    private boolean isBaseOnBaseVersion;
    private String versionName;
    private String versionDescription;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public boolean isBaseOnBaseVersion() {
        return isBaseOnBaseVersion;
    }
    
    public void setBaseOnBaseVersion(boolean baseOnBaseVersion) {
        isBaseOnBaseVersion = baseOnBaseVersion;
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
}
