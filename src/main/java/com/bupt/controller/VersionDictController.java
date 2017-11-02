package com.bupt.controller;

import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.VersionDictCreateInfo;
import com.bupt.pojo.VersionDictDTO;
import com.bupt.service.UserService;
import com.bupt.service.VersionDictService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 版本字典Controller层
 */

@RestController
@Api(tags = "VersionDict", description = "版本字典相关操作")
@RequestMapping(value = "/versionDicts")
@VersionCheckException(reason = "版本字典不需要进行版本检查")
public class VersionDictController {

    @Resource
    private VersionDictService versionDictService;
    @Resource
    private UserService userService;

    @ApiOperation(value = "查询全部版本字典信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<VersionDictDTO> listUser() {
        return versionDictService.listVersionDict();
    }


    @ApiOperation(value = "批量删除指定id的版本字典")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> versionDictIdList) {
        if (versionDictIdList.contains(100000000000L)) {
            throw new IllegalArgumentException("基础字典不允许删除");
        }
        this.versionDictService.listRemoveVersionDict(versionDictIdList);
    }


    @ApiOperation(value = "更新版本字典信息")
    @RequestMapping(value = "/{versionDictId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDictDTO updateVersionDict(@PathVariable Long versionDictId, @RequestBody VersionDictCreateInfo versionDictCreateInfo) {
        if (versionDictId == 100000000000L) {
            throw new IllegalArgumentException("基础字典的信息不允许改动");
        }
        checkVersionDictInfo(versionDictCreateInfo);
        return versionDictService.updateVersionDict(versionDictId, versionDictCreateInfo);
    }

    @ApiOperation(value = "创建新版本字典")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDictDTO saveVersionDict(@RequestBody VersionDictCreateInfo versionDictCreateInfo) {
        checkVersionDictInfo(versionDictCreateInfo);
        return versionDictService.saveVersionDict(versionDictCreateInfo);
    }


    private void checkVersionDictInfo(VersionDictCreateInfo versionDictCreateInfo) {
        if (!userService.listUserNames().contains(versionDictCreateInfo.getCreatorName().trim())) {
            throw new IllegalArgumentException("在数据库中找不到创建者相关记录！");
        }
        if (versionDictCreateInfo.getVersionDictName().trim().equals("基础字典")) {
            throw new IllegalArgumentException("字典名称不能为”基础字典“");
        }
    }
}
