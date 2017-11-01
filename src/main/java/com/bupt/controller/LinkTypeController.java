package com.bupt.controller;

import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.LinkTypeCreateInfo;
import com.bupt.pojo.LinkTypeDTO;
import com.bupt.service.LinkTypeService;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 * 链路类型:控制层
 */
@RestController
@Api(tags = "linkType", description = "链路类型相关操作")
@RequestMapping("/linkTypes")
public class LinkTypeController {
    @Resource
    private LinkTypeService linkTypeService;

    @ApiOperation(value = "更新", notes = "修改链路类型")
    @RequestMapping(value = "/{versionId}/{linkTypeId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public LinkTypeDTO updateByLinkTypeId(@PathVariable Long versionId, @PathVariable Long linkTypeId, @RequestBody
            LinkTypeCreateInfo linkTypeCreateInfo) {
        LinkTypeDTO result = linkTypeService.updateByLinkTypeId(versionId, linkTypeId, linkTypeCreateInfo);
        if (result == null) {
            throw new NoneUpdateException();
        }
        return result;
    }

    /**
     * 批量删除, 根据linkTypeId
     *
     * @param linkTypeId
     */
    @ApiOperation(value = "删除", notes = "批量删除,根据linkTypeId")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByLinkTypeId(@PathVariable Long versionId, @RequestBody List<Long> linkTypeId) {
        if (!linkTypeService.deleteByLinkTypeId(versionId, linkTypeId))
            throw new NoneRemoveException();
    }

    /**
     * 添加新链路类型
     *
     * @param linkTypeCreateInfo
     * @return
     */
    @ApiOperation(value = "增加", notes = "添加新链路类型")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LinkTypeDTO createLinkType(@PathVariable Long versionId, @RequestBody LinkTypeCreateInfo linkTypeCreateInfo) {
        return linkTypeService.createLinkType(versionId, linkTypeCreateInfo);
    }

    /**
     * 获取所有链路信息
     *
     * @param versionId
     * @return
     */
    @ApiOperation(value = "查询", notes = "获取所有链路信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<LinkTypeDTO> retrieveLinkTypes(@PathVariable Long versionId) {
        return linkTypeService.listLinkTypes(versionId);
    }


}
