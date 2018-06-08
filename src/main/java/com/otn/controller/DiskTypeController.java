package com.otn.controller;

import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.AmplifierDTO;
import com.otn.service.DiskTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "DiskTypes", description = "机盘类型相关操作")
@RequestMapping("/diskTypes")
@VersionCheckException(reason = "获取信息的时候不需要进行版本检查")
public class DiskTypeController {
    @Resource
    private DiskTypeService diskTypeService;

    @ApiOperation(value = "获取指定版本下机盘类型列表")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> listDiskTypes(@PathVariable Long versionId) {
        return diskTypeService.listDiskTypes(versionId);
    }

    @ApiOperation(value = "获取指定版本下指定机盘类型下放大器类型列表")
    @RequestMapping(value = "/{versionId}/{diskType}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AmplifierDTO> listDiskTypes(@PathVariable Long versionId, @PathVariable String diskType) {
        return diskTypeService.listAmpsByDiskType(versionId, diskType);
    }

}
