package com.bupt.pojo;


import com.bupt.util.tools.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class VersionDTOWithVersionDictDTO {
    private Long versionId;
    private String versionName;
    private String versionDescription;
    private String creatorName;
    private Date gmtCreate;
    private Date gmtModified;
    private VersionDictDTO versionDict;

    public VersionDictDTO getVersionDict() {
        return versionDict;
    }

    public void setVersionDict(VersionDictDTO versionDict) {
        this.versionDict = versionDict;
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

    public String getVersionName() {
        return versionName;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public VersionDTOWithVersionDictDTO(VersionDTO versionDTO) {
        this.versionId = versionDTO.getVersionId();
        this.versionName = versionDTO.getVersionName();
        this.versionDescription = versionDTO.getVersionDescription();
        this.creatorName = versionDTO.getCreatorName();
        this.gmtCreate = versionDTO.getGmtCreate();
        this.gmtModified = versionDTO.getGmtModified();
    }
}
