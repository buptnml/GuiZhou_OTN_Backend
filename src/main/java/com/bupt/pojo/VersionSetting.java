package com.bupt.pojo;

import com.bupt.pojo.versionSettings.ResourceSetting;

import java.io.Serializable;

/**
 * 版本配置信息
 */
public class VersionSetting implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    //资源设置
    private ResourceSetting resourceSetting = new ResourceSetting();

    public ResourceSetting getResourceSetting() {
        return resourceSetting;
    }

    public void setResourceSetting(ResourceSetting resourceSetting) {
        this.resourceSetting = resourceSetting;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
