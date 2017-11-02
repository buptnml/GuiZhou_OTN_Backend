package com.bupt.controller;


import com.bupt.controller.utils.ControllerChecker;
import com.bupt.controller.utils.VersionCheckException;
import com.bupt.facade.BussinessService;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.util.exception.controller.input.NullArgumentException;
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


    @ApiOperation(value = "查询某个版本下的所有光通道信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<BussinessDTO> listBussiness(@PathVariable Long versionId) {
        return bussinessService.listBussiness(versionId);
    }


    @ApiOperation(value = "创建新光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BussinessDTO saveBussiness(@PathVariable Long versionId, @RequestBody BussinessCreateInfo bussinessCreateInfo) {
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
        ControllerChecker.checkObject(bussinessCreateInfo);
        if (null != bussinessCreateInfo.getSpareRoute()) {
            ControllerChecker.checkObject(bussinessCreateInfo.getSpareRoute());
            if (null == bussinessCreateInfo.getSpareFrequency()) {
                throw new NullArgumentException("有备用路由时备用频点不能为空！");
            }
        }

    }


}
