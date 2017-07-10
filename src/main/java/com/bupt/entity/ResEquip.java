package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_equip")
public class ResEquip {
    /**
     * 设备id
     */
    @Id
    @Column(name = "equip_id")
    private String equipId;

    /**
     * 设备名称
     */
    @Column(name = "equip_name")
    private String equipName;

    /**
     * 设备名称全称
     */
    @Column(name = "equip_name_full")
    private String equipNameFull;

    /**
     * 设备模组
     */
    @Column(name = "equip_model")
    private String equipModel;

    /**
     * 设备类型
     */
    @Column(name = "equip_type")
    private String equipType;

    /**
     * 供应商标识
     */
    @Column(name = "equip_vendor")
    private String equipVendor;

    @Column(name = "equip_major_type")
    private String equipMajorType;

    @Column(name = "equip_sync_status")
    private String equipSyncStatus;

    /**
     * 设备运行的系统类型
     */
    @Column(name = "equip_system_name")
    private String equipSystemName;

    @Column(name = "property_id")
    private String propertyId;

    /**
     * 对应的站点id
     */
    @Column(name = "station_id")
    private String stationId;

    @Column(name = "room_code")
    private String roomCode;

    /**
     * 是否故障
     */
    @Column(name = "is_fault")
    private Byte isFault;

    /**
     * 是否被删除
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
     * 获取设备id
     *
     * @return equip_id - 设备id
     */
    public String getEquipId() {
        return equipId;
    }

    /**
     * 设置设备id
     *
     * @param equipId 设备id
     */
    public void setEquipId(String equipId) {
        this.equipId = equipId == null ? null : equipId.trim();
    }

    /**
     * 获取设备名称
     *
     * @return equip_name - 设备名称
     */
    public String getEquipName() {
        return equipName;
    }

    /**
     * 设置设备名称
     *
     * @param equipName 设备名称
     */
    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    /**
     * 获取设备名称全称
     *
     * @return equip_name_full - 设备名称全称
     */
    public String getEquipNameFull() {
        return equipNameFull;
    }

    /**
     * 设置设备名称全称
     *
     * @param equipNameFull 设备名称全称
     */
    public void setEquipNameFull(String equipNameFull) {
        this.equipNameFull = equipNameFull == null ? null : equipNameFull.trim();
    }

    /**
     * 获取设备模组
     *
     * @return equip_model - 设备模组
     */
    public String getEquipModel() {
        return equipModel;
    }

    /**
     * 设置设备模组
     *
     * @param equipModel 设备模组
     */
    public void setEquipModel(String equipModel) {
        this.equipModel = equipModel == null ? null : equipModel.trim();
    }

    /**
     * 获取设备类型
     *
     * @return equip_type - 设备类型
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * 设置设备类型
     *
     * @param equipType 设备类型
     */
    public void setEquipType(String equipType) {
        this.equipType = equipType == null ? null : equipType.trim();
    }

    /**
     * 获取供应商标识
     *
     * @return equip_vendor - 供应商标识
     */
    public String getEquipVendor() {
        return equipVendor;
    }

    /**
     * 设置供应商标识
     *
     * @param equipVendor 供应商标识
     */
    public void setEquipVendor(String equipVendor) {
        this.equipVendor = equipVendor == null ? null : equipVendor.trim();
    }

    /**
     * @return equip_major_type
     */
    public String getEquipMajorType() {
        return equipMajorType;
    }

    /**
     * @param equipMajorType
     */
    public void setEquipMajorType(String equipMajorType) {
        this.equipMajorType = equipMajorType == null ? null : equipMajorType.trim();
    }

    /**
     * @return equip_sync_status
     */
    public String getEquipSyncStatus() {
        return equipSyncStatus;
    }

    /**
     * @param equipSyncStatus
     */
    public void setEquipSyncStatus(String equipSyncStatus) {
        this.equipSyncStatus = equipSyncStatus == null ? null : equipSyncStatus.trim();
    }

    /**
     * 获取设备运行的系统类型
     *
     * @return equip_system_name - 设备运行的系统类型
     */
    public String getEquipSystemName() {
        return equipSystemName;
    }

    /**
     * 设置设备运行的系统类型
     *
     * @param equipSystemName 设备运行的系统类型
     */
    public void setEquipSystemName(String equipSystemName) {
        this.equipSystemName = equipSystemName == null ? null : equipSystemName.trim();
    }

    /**
     * @return property_id
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId
     */
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId == null ? null : propertyId.trim();
    }

    /**
     * 获取对应的站点id
     *
     * @return station_id - 对应的站点id
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * 设置对应的站点id
     *
     * @param stationId 对应的站点id
     */
    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    /**
     * @return room_code
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * @param roomCode
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode == null ? null : roomCode.trim();
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
     * 获取是否被删除
     *
     * @return is_deleted - 是否被删除
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否被删除
     *
     * @param isDeleted 是否被删除
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