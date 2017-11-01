package com.bupt.facade;

import com.bupt.entity.ResLink;
import com.bupt.pojo.*;
import com.bupt.service.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用来保证数据完整性的切面
 */
@Component
@Aspect
public class MessageAspect {
    @Resource
    private
    NetElementService netElementService;
    @Resource
    private
    BussinessService bussinessService;
    @Resource
    private
    LinkService linkService;
    @Resource
    private
    AmplifierService amplifierService;
    @Resource
    private
    DiskService diskService;
    @Resource
    private
    LinkTypeService linkTypeService;


    /*增、删、改机盘，均需要重新计算OSNR值*/
    @AfterReturning(value =
            "(execution(* com.bupt.service.DiskService.saveDisk(..)) && args(versionId,netElementId,..)) ||" +
                    "(execution(* com.bupt.service.DiskService.updateDisk(..)) && args(versionId,netElementId,..)) ||" +
                    "(execution(* com.bupt.service.DiskService.listRemove(..)) && args(versionId,netElementId,..))",
            argNames = "versionId,netElementId")
    public void updateBussinessFromDisk(Long versionId, Long netElementId) {
        NetElementDTO netElementDTO = netElementService.getNetElement(versionId, netElementId);
        bussinessService.updateReferBussiness(versionId, netElementDTO.getNetElementName(), netElementDTO.getNetElementName());
    }

    /*删除网元的时候要删除链路*/
    @AfterReturning(value = "execution(* com.bupt.service.NetElementService.listRemoveNetElement(..)) && args(versionId,netElementIdList)",
            argNames = "versionId,netElementIdList")
    public void removeLinkFromNetElement(Long versionId, List<Long> netElementIdList) {
        netElementIdList.forEach(netElementId -> linkService.listRemoveResLink(versionId,
                linkService.getReferLink(versionId, netElementId).stream().map(ResLink::getLinkId)
                        .collect(Collectors.toList())));
    }

    /*网元更新时更新链路*/
    @Around(value = "execution(* com.bupt.service.NetElementService.updateNetElement(..)) && args(versionId," +
            "netElementId,netElementCreateInfo)", argNames = "point,versionId,netElementId,netElementCreateInfo")
    public NetElementDTO updateLinkAndBussinessFromNetElement(ProceedingJoinPoint point, Long versionId, Long netElementId,
                                                              NetElementCreateInfo netElementCreateInfo) throws Throwable {
        NetElementDTO oldNetElement = netElementService.getNetElement(versionId, netElementId);
        NetElementDTO result = (NetElementDTO) point.proceed();
        bussinessService.updateReferBussiness(versionId, oldNetElement.getNetElementName(), netElementCreateInfo
                .getNetElementName());
        linkService.getReferLink(versionId, netElementId).forEach(resLink ->
                linkService.updateResLink(versionId, resLink.getLinkId(), createUpdateLinkInfo(netElementId,
                        netElementCreateInfo, resLink))
        );
        return result;
    }


    /*更新链路时更新OSNR计算，实际情况中，不可能改变链路的两个端点，要么删除，要么会更改链路的损耗或类型等属性*/
    @Around(value = "execution(* com.bupt.service.LinkService.updateResLink(..)) && args(versionId," +
            "linkId,linkCreateInfo)", argNames = "point,versionId,linkId,linkCreateInfo")
    public LinkDTO updateBussinessFromLinkUpdate(ProceedingJoinPoint point, Long versionId, Long linkId, LinkCreateInfo
            linkCreateInfo) throws Throwable {
        String oldEndAName = linkCreateInfo.getEndAName();
        String oldEndZName = linkCreateInfo.getEndZName();
        LinkDTO newLink = (LinkDTO) point.proceed();
        LinkDTO link = linkService.getLink(versionId, linkId);
        String newEndAName = link.getEndAName();
        String newEndZName = link.getEndZName();
        bussinessService.updateReferBussiness(versionId, oldEndAName + "-" + oldEndZName, newEndAName + "-" + newEndZName);
        bussinessService.updateReferBussiness(versionId, oldEndZName + "-" + oldEndAName, newEndZName + "-" + newEndAName);
        return newLink;
    }


    /*删除链路时要重新计算相关的OSNR*/
    @Around(value = "execution(* com.bupt.service.LinkService.listRemoveResLink(..)) && args(versionId," +
            "linkIdList)", argNames = "point,versionId,linkIdList")
    public void updateBussinessFromLinkRemove(ProceedingJoinPoint point, Long versionId, List<Long> linkIdList) throws
            Throwable {
        List<LinkDTO> deleteList = linkIdList.stream().map(linkId -> linkService.getLink(versionId, linkId)).collect
                (Collectors.toList());
        point.proceed();
        deleteList.forEach(linkDTO -> {
            String endAName = linkDTO.getEndAName();
            String endZName = linkDTO.getEndZName();
            bussinessService.updateReferBussiness(versionId, endAName + "-" + endZName, endAName + "-" + endZName);
            bussinessService.updateReferBussiness(versionId, endZName + "-" + endAName, endZName + "-" + endAName);
        });
    }

    /*更新放大器的时候要更新机盘*/
    @Around(value = "execution(* com.bupt.service.AmplifierService.updateAmplifiers(..)) && args(versionId," +
            "amplifierID,amplifierCreateInfo)", argNames = "point,versionId,amplifierID,amplifierCreateInfo")
    public AmplifierDTO updateDiskFromAmplifiers(ProceedingJoinPoint point, Long versionId, Long amplifierID,
                                                 AmplifierCreateInfo amplifierCreateInfo) throws Throwable {
        AmplifierDTO oldAmp = amplifierService.getAmpById(versionId, amplifierID);
        AmplifierDTO result = (AmplifierDTO) point.proceed();
        diskService.listDiskByType(versionId, oldAmp.getAmplifierName()).forEach(diskDTO -> diskService
                .updateDisk(versionId, diskDTO.getNetElementId(), diskDTO.getDiskId(), new DiskCreateInfo(diskDTO
                        .getDiskName(), amplifierCreateInfo.getAmplifierName(), diskDTO.getSlotId())));

        return result;

    }

    @Around(value = "execution(* com.bupt.service.LinkTypeService.updateByLinkTypeId(..)) && args(versionId," +
            "linkTypeId,linkTypeCreateInfo)", argNames = "point,versionId,linkTypeId,linkTypeCreateInfo")
    public LinkTypeDTO updateLinkFromLinkTypeId(ProceedingJoinPoint point, Long versionId, Long linkTypeId,
                                                LinkTypeCreateInfo linkTypeCreateInfo) throws Throwable {
        LinkTypeDTO oldType = linkTypeService.getLinkTypeById(versionId, linkTypeId);
        LinkTypeDTO result = (LinkTypeDTO) point.proceed();
        linkService.ListLinkByType(versionId, oldType.getLinkType()).forEach(linkDTO -> {
            LinkCreateInfo updateInfo = new LinkCreateInfo();
            BeanUtils.copyProperties(linkDTO, updateInfo);
            updateInfo.setLinkType(linkTypeCreateInfo.getLinkType());
            linkService.updateResLink(versionId, linkDTO.getLinkId(), updateInfo);
        });
        return result;
    }


    private LinkCreateInfo createUpdateLinkInfo(Long netElementId, NetElementCreateInfo netElementCreateInfo, ResLink resLink) {
        LinkCreateInfo newLink = new LinkCreateInfo();
        BeanUtils.copyProperties(resLink, newLink);
        if (newLink.getEndAId().equals(netElementId)) {
            newLink.setEndAName(netElementCreateInfo.getNetElementName());
        } else if (newLink.getEndZId().equals(netElementId)) {
            newLink.setEndZName(netElementCreateInfo.getNetElementName());
        }
        newLink.setLinkName(resLink.getLinkName());
        return newLink;
    }


}
