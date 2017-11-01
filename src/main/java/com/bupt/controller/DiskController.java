package com.bupt.controller;

import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.DiskCreateInfo;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.DiskService;
import com.bupt.service.NetElementService;
import com.bupt.util.exception.controller.input.NullArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机盘操作Controller层
 */
@RestController
@Api(tags = "Disk", description = "机盘操作相关操作")
@RequestMapping(value = "/disks")
public class DiskController {
    @Resource
    private DiskService diskService;
    @Resource
    private NetElementService netElementService;


    @ApiOperation(value = "查询某个版本下某设备的所有机盘信息")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
    public List<DiskDTO> listResLink(@PathVariable Long versionId, @PathVariable Long netElementId) {
        checkNetElementId(versionId, netElementId);
        return diskService.listDiskByNetElement(versionId, netElementId);
    }


    @ApiOperation(value = "创建新机盘")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DiskDTO saveDisk(@PathVariable Long versionId, @PathVariable Long netElementId, @RequestBody
            DiskCreateInfo diskCreateInfo) {
        checkNetElementId(versionId, netElementId);
        return diskService.saveDisk(versionId, netElementId, diskCreateInfo);
    }

    @ApiOperation(value = "更新某个版本下的某设备的某机盘条目")
    @RequestMapping(value = "/{versionId}/{netElementId}/{diskId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public DiskDTO updateDisk(@PathVariable Long versionId, @PathVariable Long netElementId, @PathVariable Long diskId,
                              @RequestBody DiskCreateInfo diskCreateInfo) {
        checkNetElementId(versionId, netElementId);
        return diskService.updateDisk(versionId, netElementId, diskId, diskCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下某设备的某机盘条目")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemove(@PathVariable Long versionId, @PathVariable Long netElementId, @RequestBody List<Long>
            diskIdList) {
        checkNetElementId(versionId, netElementId);
        this.diskService.listRemove(versionId, netElementId, diskIdList);
    }

    //TODO 网元中的类型信息应该来自机盘表

    /**
     * 检查网元id输入的合法性
     *
     * @param netElementId
     */
    private void checkNetElementId(Long versionId, Long netElementId) {
        if (null == netElementService.getNetElement(versionId, netElementId)) {
            throw new NullArgumentException("数据库中没有指定的网元信息！");
        }
    }


}
