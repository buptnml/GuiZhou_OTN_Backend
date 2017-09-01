package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_osnr_channel")
public class ResOsnrChannel {
    /**
     * 波道id
     */
    @Id
    @Column(name = "channel_id")
    private Long channelId;

    /**
     * 波道名
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 波道速率
     */
    @Column(name = "channel_rate")
    private String channelRate;

    /**
     * 波道频段
     */
    @Column(name = "channel_frequency")
    private String channelFrequency;

    /**
     * 波道路由
     */
    @Column(name = "channel_route")
    private String channelRoute;

    /**
     * 波道路由上设备的输入功率
     */
    @Column(name = "input_powers")
    private String inputPowers;

    /**
     * 波道路由上设备的输出功率
     */
    @Column(name = "output_powers")
    private String outputPowers;

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
     * 获取波道id
     *
     * @return channel_id - 波道id
     */
    public Long getChannelId() {
        return channelId;
    }

    /**
     * 设置波道id
     *
     * @param channelId 波道id
     */
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取波道名
     *
     * @return channel_name - 波道名
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置波道名
     *
     * @param channelName 波道名
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    /**
     * 获取波道速率
     *
     * @return channel_rate - 波道速率
     */
    public String getChannelRate() {
        return channelRate;
    }

    /**
     * 设置波道速率
     *
     * @param channelRate 波道速率
     */
    public void setChannelRate(String channelRate) {
        this.channelRate = channelRate == null ? null : channelRate.trim();
    }

    /**
     * 获取波道频段
     *
     * @return channel_frequency - 波道频段
     */
    public String getChannelFrequency() {
        return channelFrequency;
    }

    /**
     * 设置波道频段
     *
     * @param channelFrequency 波道频段
     */
    public void setChannelFrequency(String channelFrequency) {
        this.channelFrequency = channelFrequency == null ? null : channelFrequency.trim();
    }

    /**
     * 获取波道路由
     *
     * @return channel_route - 波道路由
     */
    public String getChannelRoute() {
        return channelRoute;
    }

    /**
     * 设置波道路由
     *
     * @param channelRoute 波道路由
     */
    public void setChannelRoute(String channelRoute) {
        this.channelRoute = channelRoute == null ? null : channelRoute.trim();
    }

    /**
     * 获取波道路由上设备的输入功率
     *
     * @return input_powers - 波道路由上设备的输入功率
     */
    public String getInputPowers() {
        return inputPowers;
    }

    /**
     * 设置波道路由上设备的输入功率
     *
     * @param inputPowers 波道路由上设备的输入功率
     */
    public void setInputPowers(String inputPowers) {
        this.inputPowers = inputPowers == null ? null : inputPowers.trim();
    }

    /**
     * 获取波道路由上设备的输出功率
     *
     * @return output_powers - 波道路由上设备的输出功率
     */
    public String getOutputPowers() {
        return outputPowers;
    }

    /**
     * 设置波道路由上设备的输出功率
     *
     * @param outputPowers 波道路由上设备的输出功率
     */
    public void setOutputPowers(String outputPowers) {
        this.outputPowers = outputPowers == null ? null : outputPowers.trim();
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