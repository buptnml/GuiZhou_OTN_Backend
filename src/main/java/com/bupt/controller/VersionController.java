package com.bupt.controller;

import com.bupt.facade.VersionService;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.pojo.VersionQuery;
import com.bupt.service.UserService;
import com.bupt.service.VersionDictService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 版本管理Controller层
 */
//TODO 应当消除所有的主键报错
@RestController
@Api(tags = "Version", description = "版本相关操作")
@RequestMapping(value = "/versions")
public class VersionController {
    @Resource
    private VersionService versionService;
    @Resource
    private VersionDictService versionDictService;
    @Resource
    private UserService userService;

    @ApiOperation(value = "查询所有版本")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VersionDTO> listVersion() {
        return versionService.listVersion();
    }

    @ApiOperation(value = "创建新版本")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO saveVersion(@RequestBody VersionQuery versionQuery) {
        checkVersionQuery(versionQuery);
        return versionService.saveVersion(versionQuery);
    }

    @ApiOperation(value = "同步数据")
    @RequestMapping(value = "synchronize/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String dataSynchronize(@PathVariable Long versionId, Long fromVersionId) {
        if (versionId == 100000000000L) {
            throw new IllegalArgumentException("不允许同步基础版本数据");
        }
        if (null == fromVersionId) {
            fromVersionId = 100000000000L;
        }
        versionService.dataSynchronize(fromVersionId, versionId);
        return "数据同步成功！";
    }

    @ApiOperation(value = "批量删除指定id的版本")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveVersion(@RequestBody List<Long> versionIdList) {
        if (null == versionIdList || versionIdList.size() == 0) {
            throw new IllegalArgumentException("versionIdList");
        }
        if (versionIdList.contains(100000000000L)) {
            throw new IllegalArgumentException("基础版本信息不允许修改!");
        }
        versionService.listRemoveVersion(versionIdList);
    }

    @ApiOperation(value = "按id查询版本")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VersionDTOWithVersionDictDTO getVersion(@PathVariable Long versionId) {
        return versionService.getVersion(versionId);
    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO updateVersion(@PathVariable Long versionId, @RequestBody VersionQuery versionQuery) {
        if (versionId == 100000000000L) {
            throw new IllegalArgumentException("基础版本信息不允许修改!");
        }
        return versionService.updateVersion(versionId, versionQuery);
    }


    private void checkVersionQuery(VersionQuery versionQuery) {
        if (null == versionQuery.getVersionName()) {
            throw new NullArgumentException("versionName");
        }
        if (null == versionQuery.getVersionDictName()) {
            throw new NullArgumentException("versionDictName");
        }
        if (null == versionQuery.getCreatorName()) {
            throw new NullArgumentException("creatorName");
        }
        if (null == userService.getUserByName(versionQuery.getCreatorName().trim())) {
            throw new IllegalArgumentException("创建者名称不能为空");
        }
        if (null == versionDictService.getVersionDictByName(versionQuery.getVersionDictName())) {
            throw new IllegalArgumentException("versionDictName");
        }
        if (versionQuery.getVersionName().trim().equals("基础版本")) {
            throw new IllegalArgumentException("versionName==基础版本");
        }
    }
}
