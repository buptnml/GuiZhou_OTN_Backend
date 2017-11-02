package com.bupt.controller;


import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.NetElementCreateInfo;
import com.bupt.pojo.NetElementDTO;
import com.bupt.service.NetElementService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.EnumSet;
import java.util.List;

/**
 * 网元操作Controller层
 */
@RestController
@Api(tags = "NetElement", description = "网元相关操作")
@RequestMapping(value = "/netElements")
public class NetElementController {

    @Resource
    private NetElementService netElementService;

    /**
     * 查询所有网元
     *
     * @return
     */
    @ApiOperation(value = "查询某个版本下的所有网元信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息时不需要进行版本检查")
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
        checkNetElementCreateInfo(netElementCreateInfo);
        return netElementService.updateNetElement(versionId, netElementId, netElementCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下指定Id的网元条目")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveNetElement(@PathVariable Long versionId, @RequestBody List<Long> netElementIdList) {
        this.netElementService.listRemoveNetElement(versionId, netElementIdList);
    }

    private void checkNetElementCreateInfo(NetElementCreateInfo netElementCreateInfo) {
        if (netElementCreateInfo.getNetElementName().contains("-")) {
            throw new IllegalArgumentException("名称不允许包含'-'字符！");
        }
        if (EnumSet.allOf(NetElementTypes.class).stream().filter(e -> e.toString().equals(netElementCreateInfo
                .getNetElementType())).count() == 0) {
            throw new IllegalArgumentException("输入的类型不支持，请重新输入");
        }
    }


    //网元类型枚举
    public enum NetElementTypes {
        OTM, OLA
    }


}















