package com.otn.controller;

import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.VersionDictCreateInfo;
import com.otn.pojo.VersionDictDTO;
import com.otn.service.UserService;
import com.otn.service.VersionDictService;
import com.otn.util.exception.controller.input.IllegalArgumentException;
import com.otn.webservice.com.pojo.WebServiceConfigInfo;
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
    @Resource
    private WebServiceConfigInfo webServiceConfigInfo;

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
        if (versionDictIdList.contains(webServiceConfigInfo.getBASIC_VERSION_ID())) {
            throw new IllegalArgumentException("基础字典不允许删除");
        }
        this.versionDictService.listRemoveVersionDict(versionDictIdList);
    }


    @ApiOperation(value = "更新版本字典信息")
    @RequestMapping(value = "/{versionDictId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDictDTO updateVersionDict(@PathVariable Long versionDictId, @RequestBody VersionDictCreateInfo versionDictCreateInfo) {
        if (versionDictId == webServiceConfigInfo.getBASIC_VERSION_ID()) {
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
