package com.bupt.controller;

import com.bupt.facade.VersionService;
import com.bupt.pojo.VersionCreateInfo;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOLess;
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
@RestController
@Api(tags = "Version", description = "版本相关操作")
@RequestMapping(value = "/versions")
public class VersionController {
    @Resource
    private VersionService versionService;


    @ApiOperation(value = "查询所有版本")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VersionDTOLess> listUser() {
        return versionService.listVersion();
    }

    @ApiOperation(value = "创建新版本")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO saveUser(@RequestBody VersionCreateInfo versionCreateInfo) {
        checkVersionCreateInfoLegal(versionCreateInfo);
        return versionService.saveVersion(versionCreateInfo);
    }


    @ApiOperation(value = "批量删除指定id的版本")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> versionIdList) {
        if (null == versionIdList || versionIdList.size() == 0) {
            throw new IllegalArgumentException("versionIdList");
        }
        versionService.listRemoveVersion(versionIdList);
    }

    @ApiOperation(value = "按id查询版本")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VersionDTO getUserByUserQuery(@PathVariable Long versionId) {
        return versionService.getVersion(versionId);
    }


    @ApiOperation(value = "根据ID更新版本")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO updateUser(@PathVariable Long versionId, @RequestBody VersionDTO versionDTO) {
        if (null == versionDTO.getVersionSetting()) {
            throw new NullArgumentException("versionSetting");
        }
        if (null == versionDTO.getVersionName() || versionDTO.getVersionName().trim().length() == 0) {
            throw new NullArgumentException("versionName");
        }
        versionDTO.setVersionId(versionId);
        return versionService.updateVersion(versionId, versionDTO);
    }


    private void checkVersionCreateInfoLegal(VersionCreateInfo vfi) {

        if (null == vfi.getBaseVersionName() || vfi.getBaseVersionName().trim().length() == 0) {
            throw new NullArgumentException("baseVersionName");
        }
        if (null == vfi.getVersionSetting()) {
            throw new NullArgumentException("versionSetting");
        }
        if (null == vfi.getVersionName() || vfi.getVersionName().trim().length() == 0) {
            throw new NullArgumentException("versionName");
        }
        if (vfi.getVersionName().equals(vfi.getBaseVersionName())) {
            throw new IllegalArgumentException("baseVersionName & versionName");
        }
    }
}
