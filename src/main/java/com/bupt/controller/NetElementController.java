package com.bupt.controller;


import com.bupt.pojo.NetElementCreateInfo;
import com.bupt.pojo.NetElementDTO;
import com.bupt.service.NetElementService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 网元操作Controller层
 */
@RestController
@Api(tags = "NetElement", description = "网元相关操作")
@RequestMapping(value = "/netElements")
public class NetElementController {

    @Resource
    public NetElementService netElementService;

    //网元类型枚举
    public enum NetElementTypes {
        OTM, OLA
    }

    /**
     * 查询所有网元
     *
     * @return
     */
    @ApiOperation(value = "查询某个版本下的所有网元信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<NetElementDTO> listNetElement(@PathVariable Long versionId) {
        return netElementService.listNetElement(versionId);
    }


    /**
     * 创建新网元
     *
     * @param netElementCreateInfo
     * @return
     */
    @ApiOperation(value = "创建新网元条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public NetElementDTO saveNetElement(@PathVariable Long versionId, @RequestBody NetElementCreateInfo
            netElementCreateInfo) {
        checkVersionId(versionId);
        checkNetElementCreateInfo(netElementCreateInfo);
        return netElementService.saveNetElement(versionId, netElementCreateInfo);
    }

    /**
     * 更新网元信息
     *
     * @param netElementCreateInfo
     * @return
     */
    @ApiOperation(value = "更新某个版本下的某网元条目")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public NetElementDTO updateNetElement(@PathVariable Long versionId, @PathVariable Long netElementId,
                                          @RequestBody NetElementCreateInfo
                                                  netElementCreateInfo) {
        checkVersionId(versionId);
        checkNetElementCreateInfo(netElementCreateInfo);
        return netElementService.updateNetElement(versionId, netElementId, netElementCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下指定Id的网元条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveNetElement(@PathVariable Long versionId, @RequestBody List<Long> netElementIdList) {
        checkVersionId(versionId);
        if (netElementIdList == null || netElementIdList.size() == 0) {
            throw new IllegalArgumentException("netElementIdList should not be empty.");
        }
        this.netElementService.listRemoveNetElement(versionId,netElementIdList);
    }


    private void checkNetElementCreateInfo(NetElementCreateInfo netElementCreateInfo) {
        if (null == netElementCreateInfo.getNetElementName()) {
            throw new NullArgumentException("netElementName should not be empty.");
        } else if (netElementCreateInfo.getNetElementName().trim().equals("")) {
            throw new IllegalArgumentException("netElementName should not be empty.");
        }
        if (null == netElementCreateInfo.getNetElementType()) {
            throw new NullArgumentException("netElementType should not be null.");
        } else if (null == NetElementTypes.valueOf(netElementCreateInfo.getNetElementType())) {
            throw new IllegalArgumentException("netElementType should not be null.");
        }
    }

    private void checkVersionId(Long versionID) {
        if (versionID == 100000000000L) {
            throw new java.lang.IllegalArgumentException("versionID should not be 100000000000, the base version " +
                    "could not be altered in anyway！");
        }
    }


}















