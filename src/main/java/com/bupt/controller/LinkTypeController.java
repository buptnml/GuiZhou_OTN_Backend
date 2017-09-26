package com.bupt.controller;

import com.bupt.pojo.LinkTypeDTO;
import com.bupt.service.LinkTypeService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 * 链路类型:控制层
 */
@RestController
@Api(value = "linkType",tags = "链路类型相关操作")
@RequestMapping("/linkTypes")
public class LinkTypeController {
    @Autowired
    private LinkTypeService linkTypeService;


    /**修改链路类型, 根据 linkTypeId
     * @param linkTypeId
     * @param linkTypeDTO
     * @return
     */
    @ApiOperation(value = "更新",notes = "修改链路类型")
    @RequestMapping(value = "/{versionId}/{linkTypeId}",method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public LinkTypeDTO updateByLinkTypeId(@PathVariable Long versionId,@PathVariable Long linkTypeId, @RequestBody LinkTypeDTO linkTypeDTO){
        if(linkTypeDTO==null)
            throw new IllegalArgumentException();
        LinkTypeDTO result=linkTypeService.updateByLinkTypeId(linkTypeId,linkTypeDTO);
        if(result==null)
            throw new NoneUpdateException();
        return result;
    }

    /**批量删除, 根据versionId
     * @param versionId
     */
    @ApiOperation(value = "删除",notes = "批量删除,根据版本ID")
    @RequestMapping(value = "/{versionId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByVersionId(@PathVariable Long versionId){
        if(linkTypeService.deleteByVersionId(versionId)==false)
            throw new NoneRemoveException();
    }

    /**批量删除, 根据linkTypeId
     * @param linkTypeId
     */
    @ApiOperation(value = "删除",notes="批量删除,根据linkTypeId")
    @RequestMapping(value = "/",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByLinkTypeId(@RequestBody List<Long> linkTypeId){
        if(linkTypeId.size()==0)
            throw new IllegalArgumentException();
        if(linkTypeService.deleteByLinkTypeId(linkTypeId)==false)
            throw new NoneRemoveException();
    }

    /**添加新链路类型
     * @param linkTypeDTO
     * @return
     */
    @ApiOperation(value = "增加",notes="添加新链路类型")
    @RequestMapping(value = "/{versionId}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LinkTypeDTO createLinkType(@PathVariable Long versionId,@RequestBody LinkTypeDTO linkTypeDTO){
        if(linkTypeDTO==null)
            throw new IllegalArgumentException();
        LinkTypeDTO result=linkTypeService.createLinkType(versionId,linkTypeDTO);
        if(result==null)
            throw new NoneSaveException();
        return result;
    }

    /**获取所有链路信息
     * @param versionId
     * @return
     */
    @ApiOperation(value="读取查询",notes="获取所有链路信息")
    @RequestMapping(value = "/{versionId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    private List<LinkTypeDTO> retrieveLinkTypes(@PathVariable Long versionId){
        List<LinkTypeDTO> result=linkTypeService.retrieveLinkTypes(versionId);
        if(result.size()==0)
            throw new NoneGetException();
        return result;
    }
}
