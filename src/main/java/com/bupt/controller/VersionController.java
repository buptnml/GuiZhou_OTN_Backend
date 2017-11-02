package com.bupt.controller;

import com.bupt.controller.utils.InputCheckException;
import com.bupt.controller.utils.VersionCheckException;
import com.bupt.facade.VersionService;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.pojo.VersionQuery;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
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


    @ApiOperation(value = "同步数据")
    @RequestMapping(value = "synchronize/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @InputCheckException(reason = "入参允许为null，不需要检查，由函数本身保证入参安全")
    public response dataSynchronize(@PathVariable Long versionId, Long fromVersionId) {
        if (versionId == 100000000000L) {
            throw new IllegalArgumentException("不允许同步基础版本数据");
        }
        if (null == fromVersionId) {
            fromVersionId = 100000000000L;
        }
        versionService.dataSynchronize(fromVersionId, versionId);
        return new response("同步成功！");
    }


    @ApiOperation(value = "查询所有版本")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "加入该注解的目的是为了协助输入参数检查")
    public List<VersionDTO> listVersion() {
        return versionService.listVersion();
    }

    @ApiOperation(value = "创建新版本")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO saveVersion(@RequestBody VersionQuery versionQuery) {
        return versionService.saveVersion(versionQuery);
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
    @VersionCheckException(reason = "加入该注解的目的是为了协助输入参数检查")
    public VersionDTOWithVersionDictDTO getVersion(@PathVariable Long versionId) {
        return versionService.getVersion(versionId);
    }

    @ApiOperation(value = "")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO updateVersion(@PathVariable Long versionId, @RequestBody VersionQuery versionQuery) {
        return versionService.updateVersion(versionId, versionQuery);
    }

    private class response {
        private String responseMessage;

        response(String responseMessage) {
            this.responseMessage = responseMessage;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }
    }


}
