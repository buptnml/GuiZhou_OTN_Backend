package com.bupt.controller;

import com.bupt.pojo.AmplifierCreateInfo;
import com.bupt.pojo.AmplifierDTO;
import com.bupt.service.AmplifierService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caoxiaohong on 17/9/13.
 * 放大器:控制层
 */
@RestController
@Api(tags = "Amplifier", description = "放大器相关操作")
@RequestMapping("/amplifiers")
public class AmplifierController {
    @Resource
    private AmplifierService amplifierService;

    @ApiOperation(value = "更新", notes = "修改放大器")
    @RequestMapping(value = "/{versionId}/{amplifierId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public AmplifierDTO updateAmplifiers(@PathVariable Long amplifierId, @PathVariable Long versionId,
                                         @RequestBody AmplifierCreateInfo amplifierCreateInfo) {
        checkAmplifierCreateInfo(amplifierCreateInfo);
        checkVersionId(versionId);
        return amplifierService.updateAmplifiers(versionId, amplifierId, amplifierCreateInfo);
    }

    //根据amplifierID
    @ApiOperation(value = "删除", notes = "删除放大器:根据amplifierId")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAmpid(@PathVariable Long versionId, @RequestBody List<Long> amplifierId) {
        checkVersionId(versionId);
        if (amplifierId == null || amplifierId.size() == 0)
            throw new NegativeArraySizeException();
        else {
            if (!amplifierService.deleteByAmpid(versionId, amplifierId)) {
                throw new NoneRemoveException();
            }
        }
    }

    //添加放大器 /amplifiers/:versionId/
    @ApiOperation(value = "增加", notes = "增加放大器")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AmplifierDTO insertAmplifier(@PathVariable Long versionId, @RequestBody AmplifierCreateInfo
            amplifierCreateInfo) {
        checkAmplifierCreateInfo(amplifierCreateInfo);
        checkVersionId(versionId);
        return amplifierService.insertAmplifier(versionId, amplifierCreateInfo);
    }

    //获取所有放大器 /amplifiers/:versionId
    @ApiOperation(value = "查找", notes = "查找当前版本下所有放大器")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AmplifierDTO> selectAmplifiers(@PathVariable Long versionId) {
        List<AmplifierDTO> result = amplifierService.selectAmplifiers(versionId);
        if (result == null || result.size() <= 0)
            throw new NoneGetException();
        return result;
    }


    private void checkVersionId(Long versionID) {
        if (versionID == 100000000000L) {
            throw new IllegalArgumentException("versionID should not be 100000000000, the base version " +
                    "could not be altered in anyway！");
        }
    }

    private void checkAmplifierCreateInfo(AmplifierCreateInfo amplifierCreateInfo) {
        if (null == amplifierCreateInfo.getAmplifierName()) {
            throw new NullArgumentException("amplifierName");
        }
    }
}
