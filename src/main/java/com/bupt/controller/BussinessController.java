package com.bupt.controller;


import com.bupt.service.BussinessService;
import com.bupt.facade.VersionService;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.pojo.VersionDTO;
import com.bupt.service.VersionDictService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 光通道（业务）层的Controller层
 */
@RestController
@Api(tags = "Bussiness", description = "光通道（业务）相关操作")
@RequestMapping(value = "/bussiness")
public class BussinessController {
    @Resource
    private BussinessService bussinessService;
    @Resource
    private VersionService versionService;
    @Resource
    private VersionDictService versionDictService;

    @ApiOperation(value = "查询某个版本下的所有光通道信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BussinessDTO> listBussiness(@PathVariable Long versionId) {
        checkVersionId(versionId);
        return bussinessService.listBussiness(versionId);
    }

    @ApiOperation(value = "创建新光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BussinessDTO saveBussiness(@PathVariable Long versionId, @RequestBody BussinessCreateInfo bussinessCreateInfo) {
        checkVersionId(versionId);
        bussinessCreateInfo.checkBussinessCreateInfoLegal();
        return bussinessService.saveBussiness(versionId,bussinessCreateInfo);
    }

    @ApiOperation(value = "更新某个版本下的某光通道条目")
    @RequestMapping(value = "/{versionId}/{bussinessId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public BussinessDTO updateBussiness(@PathVariable Long versionId,@PathVariable Long bussinessId,@RequestBody BussinessCreateInfo
            bussinessCreateInfo) {
        checkVersionId(versionId);
        bussinessCreateInfo.checkBussinessCreateInfoLegal();
        return bussinessService.updateBussiness(versionId,bussinessId,bussinessCreateInfo);
    }


    @ApiOperation(value = "批量删除某版本下指定Id的光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveBussiness(@PathVariable Long versionId,@RequestBody List<Long> bussinessIdList) {
        if (null == bussinessIdList || bussinessIdList.size() == 0) {
            throw new IllegalArgumentException("bussinessIdList");
        }
        this.bussinessService.listRemove(versionId,bussinessIdList);
    }


    private void checkVersionId(Long versionId){
        if(versionDictService.getVersionDictByName(versionService.getVersion(versionId).getVersionDictName())
                .getHasBussiness() ){
            throw new IllegalArgumentException("versionId");
        }
    }



}
