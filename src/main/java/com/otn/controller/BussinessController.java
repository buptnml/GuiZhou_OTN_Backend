package com.otn.controller;


import com.otn.controller.util.VersionCheckException;
import com.otn.facade.BussinessService;
import com.otn.pojo.BussinessCreateInfo;
import com.otn.pojo.BussinessDTO;
import com.otn.util.exception.controller.input.NullArgumentException;
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


    @ApiOperation(value = "更新某个版本下的某设备的某机盘条目")
    @RequestMapping(value = "/{versionId}/{bussinessId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized BussinessDTO updateBussiness(@PathVariable Long versionId, @PathVariable Long bussinessId,
                                                     @RequestBody BussinessCreateInfo
            bussinessCreateInfo) {
        checkBussinessCreateInfo(bussinessCreateInfo);
        return bussinessService.updateBussiness(versionId, bussinessId, bussinessCreateInfo);
    }

    @ApiOperation(value = "查询某个版本下的所有光通道信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<BussinessDTO> listBussiness(@PathVariable Long versionId) {
        return bussinessService.listBussiness(versionId);
    }


    @ApiOperation(value = "查询某个版本下的某个环的光通道信息")
    @RequestMapping(value = "/{versionId}/{circleId}/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<BussinessDTO> listBussiness(@PathVariable Long versionId, @PathVariable String circleId) {
        return bussinessService.listBussiness(versionId,circleId);
    }

    @ApiOperation(value = "创建新光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BussinessDTO saveBussiness(@PathVariable Long versionId, @RequestBody BussinessCreateInfo
            bussinessCreateInfo) {
        checkBussinessCreateInfo(bussinessCreateInfo);
        return bussinessService.saveBussiness(versionId, bussinessCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下指定Id的光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveBussiness(@PathVariable Long versionId, @RequestBody List<Long> bussinessIdList) {
        this.bussinessService.listRemove(versionId, bussinessIdList);
    }


    private void checkBussinessCreateInfo(BussinessCreateInfo bussinessCreateInfo) {
        if (null != bussinessCreateInfo.getSpareRoute()) {
            if (null == bussinessCreateInfo.getSpareFrequency()) {
                throw new NullArgumentException("有备用路由时备用频点不能为空！");
            }
        }
    }


}
