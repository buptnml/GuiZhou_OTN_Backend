package com.bupt.pojo;

import com.bupt.util.tools.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class VersionDictDTO {
    private Long versionDictId;
    private String versionDictName;
    private String versionDictDescription;
    private String creatorName;
    private Boolean hasBussiness;
    private Boolean hasDisk;
    private Boolean hasLink;
    private Boolean hasNetElement;
    private Boolean hasLinkType;
    private Boolean hasAmplifier;
    private Date gmtCreate;
    private Date gmtModified;

    public Long getVersionDictId() {
        return versionDictId;
    }

    public void setVersionDictId(Long versionDictId) {
        this.versionDictId = versionDictId;
    }

    public String getVersionDictName() {
        return versionDictName;
    }

    public void setVersionDictName(String versionDictName) {
        this.versionDictName = versionDictName;
    }

    public String getVersionDictDescription() {
        return versionDictDescription;
    }

    public void setVersionDictDescription(String versionDictDescription) {
        this.versionDictDescription = versionDictDescription;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Boolean getHasBussiness() {
        return hasBussiness;
    }

    public void setHasBussiness(Boolean hasBussiness) {
        this.hasBussiness = hasBussiness;
    }

    public Boolean getHasDisk() {
        return hasDisk;
    }

    public void setHasDisk(Boolean hasDisk) {
        this.hasDisk = hasDisk;
    }

    public Boolean getHasLink() {
        return hasLink;
    }

    public void setHasLink(Boolean hasLink) {
        this.hasLink = hasLink;
    }

    public Boolean getHasNetElement() {
        return hasNetElement;
    }

    public void setHasNetElement(Boolean hasNetElement) {
        this.hasNetElement = hasNetElement;
    }

    public Boolean getHasLinkType() {
        return hasLinkType;
    }

    public void setHasLinkType(Boolean hasLinkType) {
        this.hasLinkType = hasLinkType;
    }

    public Boolean getHasAmplifier() {
        return hasAmplifier;
    }

    public void setHasAmplifier(Boolean hasAmplifier) {
        this.hasAmplifier = hasAmplifier;
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
}
