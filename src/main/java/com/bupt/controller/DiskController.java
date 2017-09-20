package com.bupt.controller;

import com.bupt.entity.DiskCreateInfo;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.DiskService;
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




    @ApiOperation(value = "查询某个版本下某设备的所有机盘信息")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DiskDTO> listResLink(@PathVariable Long versionId,@PathVariable Long netElementId) {
        return diskService.listDiskByNetElement(versionId,netElementId);
    }




    @ApiOperation(value = "创建新机盘")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DiskDTO saveDisk(@PathVariable Long versionId, @PathVariable Long netElementId, @RequestBody
            DiskCreateInfo diskCreateInfo) {
        checkVersionId(versionId);
        checkDiskCreateInfo(diskCreateInfo);
        return diskService.saveDisk(versionId, netElementId, diskCreateInfo);
    }

    @ApiOperation(value = "更新某个版本下的某设备的某机盘条目")
    @RequestMapping(value = "/{versionId}/{netElementId}/{diskId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public DiskDTO updateDisk(@PathVariable Long versionId, @PathVariable Long netElementId, @PathVariable Long diskId,
                              @RequestBody DiskCreateInfo diskCreateInfo) {
        checkVersionId(versionId);
        checkDiskCreateInfo(diskCreateInfo);
        return diskService.updateDisk(versionId, netElementId, diskId, diskCreateInfo);
    }

    @ApiOperation(value = "批量删除某版本下某设备的某机盘条目")
    @RequestMapping(value = "/{versionId}/{netElementId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemove(@PathVariable Long versionId, @PathVariable Long netElementId, @RequestBody List<Long>
            diskIdList) {
        if (diskIdList == null || diskIdList.size() == 0) {
            throw new IllegalArgumentException("diskIdList不能为空");
        }
        checkVersionId(versionId);
        this.diskService.listRemove(versionId, netElementId, diskIdList);
    }

    private void checkDiskCreateInfo(DiskCreateInfo diskCreateInfo){
        //TODO 机盘表中信息应该来源于机盘类型表的信息
        if(null==diskCreateInfo.getDiskName()){
            throw new NullArgumentException("diskName should not be null!");
        }if(null == diskCreateInfo.getDiskType()){
            throw new NullArgumentException("diskType should not be null");
        }
    }


    private void checkVersionId(Long versionID) {
        if (versionID == 100000000000L) {
            throw new IllegalArgumentException("versionID不能为基础版本的ID，基础版本不允许任何方式的修改！");
        }
    }
}
