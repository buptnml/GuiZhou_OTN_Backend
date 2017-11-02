package com.bupt.controller;


import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.LinkDTO;
import com.bupt.service.LinkService;
import com.bupt.service.LinkTypeService;
import com.bupt.service.NetElementService;
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
    @Resource
    private LinkTypeService linkTypeService;

    @ApiOperation(value = "查询某个版本下的所有链路信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<LinkDTO> listResLink(@PathVariable Long versionId) {
        return linkService.listLinks(versionId);
    }

    @ApiOperation(value = "创建新链路")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LinkDTO saveResLink(@PathVariable Long versionId, @RequestBody LinkCreateInfo linkCreateInfo) {
        checkLinkCreateInfo(versionId, linkCreateInfo);
        return linkService.saveResLink(versionId, linkCreateInfo);
    }

    @ApiOperation(value = "更新某个版本下的某链路条目")
    @RequestMapping(value = "/{versionId}/{linkId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public LinkDTO updateResLink(@PathVariable Long versionId, @PathVariable Long linkId, @RequestBody LinkCreateInfo
            linkCreateInfo) {
        checkLinkCreateInfo(versionId, linkCreateInfo);
        return linkService.updateResLink(versionId, linkId, linkCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下指定Id的链路条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResLink(@PathVariable Long versionId, @RequestBody List<Long> linkIdList) {
        this.linkService.listRemoveResLink(versionId, linkIdList);
    }

    private void checkLinkCreateInfo(Long versionId, LinkCreateInfo linkCreateInfo) {
        try {
            netElementService.getNetElement(versionId, linkCreateInfo.getEndAId());
        } catch (NoneGetException e) {
            throw new NoneGetException("数据库中没有找到链路A端点指定的网元信息！");
        }
        try {
            netElementService.getNetElement(versionId, linkCreateInfo.getEndZId());
        } catch (Exception e) {
            throw new NoneGetException("数据库中没有找到链路Z端点指定的网元信息！");
        }
        if (linkTypeService.listLinkTypes(versionId).stream().filter(linkTypeDTO -> linkCreateInfo.getLinkType().equals
                (linkTypeDTO.getLinkType())).count() == 0) {
            throw new IllegalArgumentException("选择的链路类型不支持");
        }
    }

}























