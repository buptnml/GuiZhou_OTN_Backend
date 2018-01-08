package com.bupt.controller;

import com.bupt.controller.util.InputCheckException;
import com.bupt.controller.util.VersionCheckException;
import com.bupt.facade.VersionConcreteService;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.pojo.VersionQuery;
import com.bupt.service.VersionBackUpService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.webservice.com.pojo.WebServiceConfigInfo;
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
    private VersionConcreteService versionConcreteService;
    @Resource
    private VersionBackUpService versionBackUpService;
    @Resource
    private WebServiceConfigInfo webServiceConfigInfo;

    @ApiOperation(value = "同步数据")
    @RequestMapping(value = "/synchronize/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @InputCheckException(reason = "入参允许为null，不需要检查，由函数本身保证入参安全")
    public ResponseInfo dataSynchronize(@PathVariable Long versionId, Long fromVersionId) {
        if (null == fromVersionId) {
            fromVersionId = webServiceConfigInfo.getBASIC_VERSION_ID();
        }
        versionConcreteService.dataSynchronize(fromVersionId, versionId);
        return new ResponseInfo("同步成功！");
    }


    @ApiOperation(value = "查询所有版本")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "加入该注解的目的是为了协助输入参数检查")
    public List<VersionDTO> listVersion() {
        return versionConcreteService.listVersion();
    }

    @ApiOperation(value = "创建新版本")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO saveVersion(@RequestBody VersionQuery versionQuery) {
        return versionConcreteService.saveVersion(versionQuery);
    }

    @ApiOperation(value = "批量删除指定id的版本")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveVersion(@RequestBody List<Long> versionIdList) {
        if (null == versionIdList || versionIdList.size() == 0) {
            throw new IllegalArgumentException("versionIdList");
        }
        if (versionIdList.contains(webServiceConfigInfo.getBASIC_VERSION_ID())) {
            throw new IllegalArgumentException("基础版本信息不允许修改!");
        }
        versionConcreteService.listRemoveVersion(versionIdList);
    }

    @ApiOperation(value = "按id查询版本")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "加入该注解的目的是为了协助输入参数检查")
    public VersionDTOWithVersionDictDTO getVersion(@PathVariable Long versionId) {
        return versionConcreteService.getVersion(versionId);
    }

    @ApiOperation(value = "更新版本信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public VersionDTO updateVersion(@PathVariable Long versionId, @RequestBody VersionQuery versionQuery) {
        return versionConcreteService.updateVersion(versionId, versionQuery);
    }

    @ApiOperation(value = "保存版本当前的数据")
    @RequestMapping(value = "/save/{versionId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseInfo saveVersion(@PathVariable Long versionId) {
        versionBackUpService.saveBackUp(versionId);
        return new ResponseInfo("保存成功！");
    }

    private class ResponseInfo {
        private String responseMessage;

        ResponseInfo(String responseMessage) {
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
