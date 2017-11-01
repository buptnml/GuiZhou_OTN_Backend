package com.bupt.pojo;

import org.springframework.lang.Nullable;

public class VersionDictCreateInfo {
    private String versionDictName;
    @Nullable
    private String versionDictDescription;
    private String creatorName;
    private Boolean hasBussiness;
    private Boolean hasDisk;
    private Boolean hasLink;
    private Boolean hasNetElement;
    private Boolean hasLinkType;
    private Boolean hasAmplifier;

    public String getVersionDictName() {
        return versionDictName;
    }

    public void setVersionDictName(String versionDictName) {
        this.versionDictName = versionDictName;
    }

    @Nullable
    public String getVersionDictDescription() {
        return versionDictDescription;
    }

    public void setVersionDictDescription(@Nullable String versionDictDescription) {
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
}
