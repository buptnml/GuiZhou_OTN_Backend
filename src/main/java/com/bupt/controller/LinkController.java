package com.bupt.controller;


import com.bupt.pojo.NetElementDTO;
import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.ResLinkDTO;
import com.bupt.service.NetElementService;
import com.bupt.service.LinkService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 链路操作Controller层
 */
@RestController
@Api(tags = "Link", description = "链路操作相关操作")
@RequestMapping(value = "/links")
public class LinkController {
    @Resource
    private LinkService linkService;
    @Resource
    private NetElementService netElementService;

    //链路类型枚举
    public enum LinkTypes {
        OPGW, ADSS, NORMAL, UNKNOWN
    }


    @ApiOperation(value = "查询某个版本下的所有链路信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ResLinkDTO> listResLink(@PathVariable Long versionId) {
        return linkService.getResLink(versionId);
    }


    @ApiOperation(value = "创建新链路")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResLinkDTO saveResLink(@PathVariable Long versionId, @RequestBody LinkCreateInfo linkCreateInfo) {
        checkVersionId(versionId);
        checkLinkCreateInfo(versionId,linkCreateInfo);
        return linkService.saveResLink(versionId, linkCreateInfo);
    }

    @ApiOperation(value = "更新某个版本下的某链路条目")
    @RequestMapping(value = "/{versionId}/{linkId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public ResLinkDTO updateResLink(@PathVariable Long versionId, @PathVariable Long linkId, @RequestBody LinkCreateInfo
            linkCreateInfo) {
        checkVersionId(versionId);
        checkLinkCreateInfo(versionId,linkCreateInfo);
        return linkService.updateResLink(versionId, linkId, linkCreateInfo);
    }


    @ApiOperation(value = "批量删除某版本下指定Id的链路条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResLink(@PathVariable Long versionId, @RequestBody List<Long> linkIdList) {
        if (linkIdList == null || linkIdList.size() == 0) {
            throw new IllegalArgumentException("linkIdList");
        }
        checkVersionId(versionId);
        this.linkService.listRemoveResLink(versionId, linkIdList);
    }

    private void checkLinkCreateInfo(Long versionId,LinkCreateInfo linkCreateInfo) {
        if(null == linkCreateInfo.getEndAId()){
            throw new NullArgumentException("endAId should not be NULL!");
        }
        if(null == linkCreateInfo.getEndZId()){
            throw new NullArgumentException("endZId should not be NULL!");
        }
        NetElementDTO endA = null;
        try {
            endA = netElementService.getNetElement(versionId,linkCreateInfo.getEndAId());
        } catch (NoneGetException e) {
            throw new NoneGetException("endAId");
        }
        NetElementDTO endZ = null;
        try {
            endZ = netElementService.getNetElement(versionId,linkCreateInfo.getEndZId());
        } catch (Exception e) {
            throw new NoneGetException("endZId");
        }
        if (null == endA) {
            throw new NullArgumentException("could not find endAId`s netElement");
        }
        if (null == endZ) {
            throw new NullArgumentException("could not find endZId`s netElement");
        }
        if (null == linkCreateInfo.getLinkType()) {
            throw new NullArgumentException("linkType could not be null");
        } else if (null == LinkTypes.valueOf(linkCreateInfo.getLinkType())) {
            throw new IllegalArgumentException("linkType input is illegal");
        }
        if (null == linkCreateInfo.getLinkName()) {
            throw new IllegalArgumentException("linkName could not be null");
        }
    }

    private void checkVersionId(Long versionID) {
        if (versionID == 100000000000L) {
            throw new IllegalArgumentException("versionID should not be 100000000000, the base version " +
                    "could not be altered in anyway！");
        }
    }
}























