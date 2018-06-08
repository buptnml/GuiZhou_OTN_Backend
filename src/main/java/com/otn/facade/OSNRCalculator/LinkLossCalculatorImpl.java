package com.otn.facade.OSNRCalculator;


import com.otn.facade.OSNRCalculator.exceptions.LinkNotFoundException;
import com.otn.pojo.LinkDTO;
import com.otn.service.LinkService;
import com.otn.service.LinkTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 链路损耗的实现类
 * 链路指的是拓扑图上两个网元之间的链路
 * 网元之间的链路可能是有多条的，因此在找链路的时候应随机选择一条作为两点之间的链路（OSNR算法要求）
 */
@Component
class LinkLossCalculatorImpl implements LinkLossCalculator {
    private LinkDTO link;
    @Resource
    private LinkService linkService;
    @Resource
    private LinkTypeService linkTypeService;

    @Override
    public double getLinkLoss(long versionId, String node1Name, String node2Name) throws LinkNotFoundException {
        init(versionId, node1Name, node2Name);
        if (link.getLinkLoss() != 0) {
            return link.getLinkLoss();
        }
        return linkTypeService.getLinkType(versionId, link.getLinkType()).getLinkLoss() * link.getLinkLength();
    }

    private void init(long versionId, String node1Name, String node2Name) {
        link = linkService.getLinkByNodes(versionId, node1Name, node2Name);
        if (null == link) {
            throw new LinkNotFoundException(node1Name, node2Name);
        }
    }

}
