package com.bupt.pojo;

public class ChannelQuery {
    private  Long versionId = null;
    private  Long bussinessId = null;
    private  Boolean isMain = null;

    /**
     */
    public ChannelQuery(Long versionId, Long bussinessId, boolean isMain) {
        this.versionId = versionId;
        this.bussinessId = bussinessId;
        this.isMain = isMain;
    }

    public ChannelQuery() {
    }

    public Long getVersionId() {
        return versionId;
    }

    public Long getBussinessId() {
        return bussinessId;
    }

    public Boolean isMain() {
        return isMain;
    }
}
