package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_service")
public class ResService {
    /**
     * 业务id
     */
    @Id
    @Column(name = "service_id")
    private Long serviceId;

    /**
     * 业务名
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 主路由
     */
    @Column(name = "route_main")
    private String routeMain;

    /**
     * 备用路由
     */
    @Column(name = "route_spare")
    private String routeSpare;

    /**
     * 主波道id
     */
    @Column(name = "main_channel_id")
    private Long mainChannelId;

    /**
     * 备用波道id
     */
    @Column(name = "spare_channel_id")
    private Long spareChannelId;

    /**
     * 条目所在版本ID
     */
    @Column(name = "version_id")
    private Long versionId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 获取业务id
     *
     * @return service_id - 业务id
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * 设置业务id
     *
     * @param serviceId 业务id
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * 获取业务名
     *
     * @return service_name - 业务名
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置业务名
     *
     * @param serviceName 业务名
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * 获取主路由
     *
     * @return route_main - 主路由
     */
    public String getRouteMain() {
        return routeMain;
    }

    /**
     * 设置主路由
     *
     * @param routeMain 主路由
     */
    public void setRouteMain(String routeMain) {
        this.routeMain = routeMain == null ? null : routeMain.trim();
    }

    /**
     * 获取备用路由
     *
     * @return route_spare - 备用路由
     */
    public String getRouteSpare() {
        return routeSpare;
    }

    /**
     * 设置备用路由
     *
     * @param routeSpare 备用路由
     */
    public void setRouteSpare(String routeSpare) {
        this.routeSpare = routeSpare == null ? null : routeSpare.trim();
    }

    /**
     * 获取主波道id
     *
     * @return main_channel_id - 主波道id
     */
    public Long getMainChannelId() {
        return mainChannelId;
    }

    /**
     * 设置主波道id
     *
     * @param mainChannelId 主波道id
     */
    public void setMainChannelId(Long mainChannelId) {
        this.mainChannelId = mainChannelId;
    }

    /**
     * 获取备用波道id
     *
     * @return spare_channel_id - 备用波道id
     */
    public Long getSpareChannelId() {
        return spareChannelId;
    }

    /**
     * 设置备用波道id
     *
     * @param spareChannelId 备用波道id
     */
    public void setSpareChannelId(Long spareChannelId) {
        this.spareChannelId = spareChannelId;
    }

    /**
     * 获取条目所在版本ID
     *
     * @return version_id - 条目所在版本ID
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * 设置条目所在版本ID
     *
     * @param versionId 条目所在版本ID
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取最后修改时间
     *
     * @return gmt_modified - 最后修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置最后修改时间
     *
     * @param gmtModified 最后修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}