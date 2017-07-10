package com.bupt.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "res_equip_coordinate")
public class ResEquipCoordinate {
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
     * 设备运行的系统类型
     */
    @Column(name = "equip_system_name")
    private String equipSystemName;

    /**
     * 设备坐标X
     */
    private BigDecimal x;

    /**
     * 设备坐标Y
     */
    private BigDecimal y;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Byte isDelete;

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
     * 获取设备坐标X
     *
     * @return x - 设备坐标X
     */
    public BigDecimal getX() {
        return x;
    }

    /**
     * 设置设备坐标X
     *
     * @param x 设备坐标X
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }

    /**
     * 获取设备坐标Y
     *
     * @return y - 设备坐标Y
     */
    public BigDecimal getY() {
        return y;
    }

    /**
     * 设置设备坐标Y
     *
     * @param y 设备坐标Y
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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