package com.bupt.controller;


import com.bupt.facade.VersionService;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.service.BussinessService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
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
    @Resource
    private VersionService versionService;



    @ApiOperation(value = "查询某个版本下的所有光通道信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BussinessDTO> listBussiness(@PathVariable Long versionId) {
        return bussinessService.listBussiness(versionId);
    }


    @ApiOperation(value = "创建新光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BussinessDTO saveBussiness(@PathVariable Long versionId, @RequestBody BussinessCreateInfo bussinessCreateInfo) {
        checkVersionId(versionId);
        checkBussinessCreateInfo(bussinessCreateInfo);
        return bussinessService.saveBussiness(versionId, bussinessCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下指定Id的光通道条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveBussiness(@PathVariable Long versionId, @RequestBody List<Long> bussinessIdList) {
        if (null == bussinessIdList || bussinessIdList.size() == 0) {
            throw new IllegalArgumentException("bussinessIdList");
        }
        this.bussinessService.listRemove(versionId, bussinessIdList);
    }


    private void checkBussinessCreateInfo(BussinessCreateInfo bussinessCreateInfo) {
        if (null == bussinessCreateInfo.getBussinessName()) {
            throw new NullArgumentException("bussinessName should not be null!");
        }
        if (null == bussinessCreateInfo.getBussinessRate()) {
            throw new NullArgumentException("bussinessRate should not be null!");
        }
        if (null == bussinessCreateInfo.getMainRoute()) {
            throw new NullArgumentException("mainRoute should not be null!");
        }
        if (null == bussinessCreateInfo.getMainFrequency()) {
            throw new NullArgumentException("mainFrequency should not be null");
        }
        if (null == bussinessCreateInfo.getInputPower()) {
            throw new NullArgumentException("inputPower should not be null");
        }
        if (null != bussinessCreateInfo.getSpareRoute()) {
            if (null == bussinessCreateInfo.getSpareFrequency()) {
                throw new NullArgumentException("spareFrequency should not be null when spareRoute exists!");
            }
        }
    }


    private void checkVersionId(Long versionId) {
        if (!versionService.getVersion(versionId).getVersionDict().getHasBussiness()) {
            throw new IllegalArgumentException("versionId");
        }
        if (versionId == 100000000000L) {
            throw new java.lang.IllegalArgumentException("versionID should not be 100000000000, the base version " +
                    "could not be altered in anyway！");
        }
    }


}
