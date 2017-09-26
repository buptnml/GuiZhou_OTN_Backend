package com.bupt.controller;

import com.bupt.pojo.VersionDictDTO;
import com.bupt.pojo.VersionDictInfo;
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
        if (null == versionDictIdList || versionDictIdList.size() == 0) {
            throw new IllegalArgumentException("versionDictIdList");
        }
        if (versionDictIdList.contains(100000000000L)) {
            throw new IllegalArgumentException("versionDictIdList contains 100000000000");
        }
        this.versionDictService.listRemoveVersionDict(versionDictIdList);
    }


    @ApiOperation(value = "更新版本字典信息")
    @RequestMapping(value = "/{versionDictId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDictDTO updateVersionDict(@PathVariable Long versionDictId, @RequestBody VersionDictInfo versionDictInfo) {
        if (versionDictId==100000000000L){
            throw new IllegalArgumentException("versionDictIdList contains 100000000000 which is the basic version " +
                    "ID");
        }
        checkVersionDictInfo(versionDictInfo);
        return versionDictService.updateVersionDict(versionDictId,versionDictInfo);
    }

    @ApiOperation(value = "创建新版本字典")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDictDTO saveVersionDict(@RequestBody VersionDictInfo versionDictInfo) {
        checkVersionDictInfo(versionDictInfo);
        return versionDictService.saveVersionDict(versionDictInfo);
    }



    private void checkVersionDictInfo(VersionDictInfo versionDictInfo){
        if(!userService.listUserNames().contains(versionDictInfo.getCreatorName().trim())){
            throw new IllegalArgumentException("CreatorName");
        }
        if(versionDictInfo.getVersionDictName().trim().equals("基础字典")){
            throw new IllegalArgumentException("versionDIctName should not be the basic version name.");
        }
    }
}
