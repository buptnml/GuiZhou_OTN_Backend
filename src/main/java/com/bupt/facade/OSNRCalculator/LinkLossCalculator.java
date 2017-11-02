package com.bupt.facade.OSNRCalculator;

/**
 * 链路损耗的计算接口
 * 目的是做一层封装，上一层不需要知道链路损耗是怎么计算的
 */
interface LinkLossCalculator {
    /**
     * 返回链路损耗
     *
     * @return
     */
    double getLinkLoss(long versionId, String node1Name, String node2Name);
}
