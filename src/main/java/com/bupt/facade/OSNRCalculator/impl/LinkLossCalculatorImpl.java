package com.bupt.facade.OSNRCalculator.impl;


import com.bupt.facade.OSNRCalculator.LinkLossCalculator;
import com.bupt.pojo.LinkDTO;
import com.bupt.service.LinkService;
import com.bupt.service.LinkTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 链路损耗的实现类
 * 链路指的是拓扑图上两个网元之间的链路
 * 网元之间的链路可能是有多条的，因此在找链路的时候应随机选择一条作为两点之间的链路（OSNR算法要求）
 */
@Component
public class LinkLossCalculatorImpl implements LinkLossCalculator {
    private LinkDTO link;
    @Resource
    private LinkService linkService;
    @Resource
    private LinkTypeService linkTypeService;

    @Override
    public double getLinkLoss(long versionId, String node1Name, String node2Name) {
        init(versionId, node1Name, node2Name);
        if (link.getLinkLoss() != 0) {
            return link.getLinkLoss();
        }
        return linkTypeService.getLinkType(versionId, link.getLinkType()).getLinkLoss() * link.getLinkLength();
    }

    private void init(long versionId, String node1Name, String node2Name) {
        link = linkService.getLinkByNodes(versionId, node1Name, node2Name);
    }

}
