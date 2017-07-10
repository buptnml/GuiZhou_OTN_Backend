package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_topology_link")
public class ResTopologyLink {
    /**
     * 链路id
     */
    @Id
    @Column(name = "link_id")
    private String linkId;

    /**
     * 链路名称
     */
    @Column(name = "link_name")
    private String linkName;

    /**
     * 链路全名
     */
    @Column(name = "link_name_full")
    private String linkNameFull;

    /**
     * 链路速率
     */
    @Column(name = "link_rate")
    private String linkRate;

    /**
     * 链路长度
     */
    @Column(name = "link_length")
    private Long linkLength;

    @Column(name = "link_sync_status")
    private String linkSyncStatus;

    /**
     * a端端口id
     */
    @Column(name = "end_a_port_id")
    private String endAPortId;

    /**
     * z端端口id
     */
    @Column(name = "end_z_port_id")
    private String endZPortId;

    /**
     * a端站点id
     */
    @Column(name = "end_a_station_id")
    private String endAStationId;

    /**
     * z端站点id
     */
    @Column(name = "end_z_station_id")
    private String endZStationId;

    /**
     * a端设备id
     */
    @Column(name = "end_a_equip_id")
    private String endAEquipId;

    /**
     * z端设备id
     */
    @Column(name = "end_z_equip_id")
    private String endZEquipId;

    /**
     * a端设备系统名称
     */
    @Column(name = "end_a_equip_system_name")
    private String endAEquipSystemName;

    /**
     * z端设备系统名称
     */
    @Column(name = "end_z_equip_system_name")
    private String endZEquipSystemName;

    /**
     * 是否故障
     */
    @Column(name = "is_fault")
    private Byte isFault;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 插入时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 创建时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 获取链路id
     *
     * @return link_id - 链路id
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * 设置链路id
     *
     * @param linkId 链路id
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId == null ? null : linkId.trim();
    }

    /**
     * 获取链路名称
     *
     * @return link_name - 链路名称
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * 设置链路名称
     *
     * @param linkName 链路名称
     */
    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    /**
     * 获取链路全名
     *
     * @return link_name_full - 链路全名
     */
    public String getLinkNameFull() {
        return linkNameFull;
    }

    /**
     * 设置链路全名
     *
     * @param linkNameFull 链路全名
     */
    public void setLinkNameFull(String linkNameFull) {
        this.linkNameFull = linkNameFull == null ? null : linkNameFull.trim();
    }

    /**
     * 获取链路速率
     *
     * @return link_rate - 链路速率
     */
    public String getLinkRate() {
        return linkRate;
    }

    /**
     * 设置链路速率
     *
     * @param linkRate 链路速率
     */
    public void setLinkRate(String linkRate) {
        this.linkRate = linkRate == null ? null : linkRate.trim();
    }

    /**
     * 获取链路长度
     *
     * @return link_length - 链路长度
     */
    public Long getLinkLength() {
        return linkLength;
    }

    /**
     * 设置链路长度
     *
     * @param linkLength 链路长度
     */
    public void setLinkLength(Long linkLength) {
        this.linkLength = linkLength;
    }

    /**
     * @return link_sync_status
     */
    public String getLinkSyncStatus() {
        return linkSyncStatus;
    }

    /**
     * @param linkSyncStatus
     */
    public void setLinkSyncStatus(String linkSyncStatus) {
        this.linkSyncStatus = linkSyncStatus == null ? null : linkSyncStatus.trim();
    }

    /**
     * 获取a端端口id
     *
     * @return end_a_port_id - a端端口id
     */
    public String getEndAPortId() {
        return endAPortId;
    }

    /**
     * 设置a端端口id
     *
     * @param endAPortId a端端口id
     */
    public void setEndAPortId(String endAPortId) {
        this.endAPortId = endAPortId == null ? null : endAPortId.trim();
    }

    /**
     * 获取z端端口id
     *
     * @return end_z_port_id - z端端口id
     */
    public String getEndZPortId() {
        return endZPortId;
    }

    /**
     * 设置z端端口id
     *
     * @param endZPortId z端端口id
     */
    public void setEndZPortId(String endZPortId) {
        this.endZPortId = endZPortId == null ? null : endZPortId.trim();
    }

    /**
     * 获取a端站点id
     *
     * @return end_a_station_id - a端站点id
     */
    public String getEndAStationId() {
        return endAStationId;
    }

    /**
     * 设置a端站点id
     *
     * @param endAStationId a端站点id
     */
    public void setEndAStationId(String endAStationId) {
        this.endAStationId = endAStationId == null ? null : endAStationId.trim();
    }

    /**
     * 获取z端站点id
     *
     * @return end_z_station_id - z端站点id
     */
    public String getEndZStationId() {
        return endZStationId;
    }

    /**
     * 设置z端站点id
     *
     * @param endZStationId z端站点id
     */
    public void setEndZStationId(String endZStationId) {
        this.endZStationId = endZStationId == null ? null : endZStationId.trim();
    }

    /**
     * 获取a端设备id
     *
     * @return end_a_equip_id - a端设备id
     */
    public String getEndAEquipId() {
        return endAEquipId;
    }

    /**
     * 设置a端设备id
     *
     * @param endAEquipId a端设备id
     */
    public void setEndAEquipId(String endAEquipId) {
        this.endAEquipId = endAEquipId == null ? null : endAEquipId.trim();
    }

    /**
     * 获取z端设备id
     *
     * @return end_z_equip_id - z端设备id
     */
    public String getEndZEquipId() {
        return endZEquipId;
    }

    /**
     * 设置z端设备id
     *
     * @param endZEquipId z端设备id
     */
    public void setEndZEquipId(String endZEquipId) {
        this.endZEquipId = endZEquipId == null ? null : endZEquipId.trim();
    }

    /**
     * 获取a端设备系统名称
     *
     * @return end_a_equip_system_name - a端设备系统名称
     */
    public String getEndAEquipSystemName() {
        return endAEquipSystemName;
    }

    /**
     * 设置a端设备系统名称
     *
     * @param endAEquipSystemName a端设备系统名称
     */
    public void setEndAEquipSystemName(String endAEquipSystemName) {
        this.endAEquipSystemName = endAEquipSystemName == null ? null : endAEquipSystemName.trim();
    }

    /**
     * 获取z端设备系统名称
     *
     * @return end_z_equip_system_name - z端设备系统名称
     */
    public String getEndZEquipSystemName() {
        return endZEquipSystemName;
    }

    /**
     * 设置z端设备系统名称
     *
     * @param endZEquipSystemName z端设备系统名称
     */
    public void setEndZEquipSystemName(String endZEquipSystemName) {
        this.endZEquipSystemName = endZEquipSystemName == null ? null : endZEquipSystemName.trim();
    }

    /**
     * 获取是否故障
     *
     * @return is_fault - 是否故障
     */
    public Byte getIsFault() {
        return isFault;
    }

    /**
     * 设置是否故障
     *
     * @param isFault 是否故障
     */
    public void setIsFault(Byte isFault) {
        this.isFault = isFault;
    }

    /**
     * 获取是否删除
     *
     * @return is_deleted - 是否删除
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否删除
     *
     * @param isDeleted 是否删除
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取插入时间
     *
     * @return gmt_create - 插入时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置插入时间
     *
     * @param gmtCreate 插入时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_modified - 创建时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置创建时间
     *
     * @param gmtModified 创建时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}