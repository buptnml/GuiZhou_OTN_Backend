package com.otn.pojo.versionSettings;

import java.io.Serializable;

public class BussinessDisplaySetting implements Serializable {
    private boolean hasBussinessId;
    private boolean hasBussinessName;
    private boolean hasMainRoute;
    private boolean hasSpareRoute;
    private boolean hasMainChannelId;
    private boolean hasSpareChannelId;
    private boolean hasMainChannelName;
    private boolean hasSpareChannleName;

    private String bussinessIdRename;
    private String bussinessNameRename;
    private String mainRouteRename;
    private String spareRouteRename;
    private String mainChannelIdRename;
    private String spareChannelIdRename;
    private String mainChannelNameRename;
    private String spareChannelNameRename;

}
