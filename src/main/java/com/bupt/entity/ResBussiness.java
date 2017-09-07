package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_bussiness")
public class ResBussiness {
    /**
     * 业务id
     */
    @Id
    @Column(name = "bussiness_id")
    private Long bussinessId;

    /**
     * 业务名
     */
    @Column(name = "bussiness_name")
    private String bussinessName;

    /**
     * 主路由
     */
    @Column(name = "main_route")
    private String mainRoute;

    /**
     * 备用路由
     */
    @Column(name = "spare_route")
    private String spareRoute;

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
     * @return bussiness_id - 业务id
     */
    public Long getBussinessId() {
        return bussinessId;
    }

    /**
     * 设置业务id
     *
     * @param bussinessId 业务id
     */
    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    /**
     * 获取业务名
     *
     * @return bussiness_name - 业务名
     */
    public String getBussinessName() {
        return bussinessName;
    }

    /**
     * 设置业务名
     *
     * @param bussinessName 业务名
     */
    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName == null ? null : bussinessName.trim();
    }

    /**
     * 获取主路由
     *
     * @return main_route - 主路由
     */
    public String getMainRoute() {
        return mainRoute;
    }

    /**
     * 设置主路由
     *
     * @param mainRoute 主路由
     */
    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute == null ? null : mainRoute.trim();
    }

    /**
     * 获取备用路由
     *
     * @return spare_route - 备用路由
     */
    public String getSpareRoute() {
        return spareRoute;
    }

    /**
     * 设置备用路由
     *
     * @param spareRoute 备用路由
     */
    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute == null ? null : spareRoute.trim();
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