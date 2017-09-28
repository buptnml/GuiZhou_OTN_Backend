package com.bupt.controller;

import com.bupt.pojo.AmplifierDTO;
import com.bupt.service.AmplifierService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/13.
 * 放大器:控制层
 */
@RestController
@Api(value = "Amplifier",tags = "放大器相关操作")
@RequestMapping("/amplifiers")
public class AmplifierController {
        @Autowired
        private   AmplifierService  amplifierService;

        //修改放大器/amplifiers/:versionId/:amplifierId. 注:未用到versionId
        @ApiOperation(value = "更新",notes="修改放大器")
        @RequestMapping(value = "/{versionId}/{amplifierId}",method =RequestMethod.PATCH)
        @ResponseStatus(HttpStatus.OK)
        public AmplifierDTO updateAmplifiers(@PathVariable Long versionId,@PathVariable Long amplifierId, @RequestBody AmplifierDTO amplifer){
            AmplifierDTO result=amplifierService.updateAmplifiers(amplifierId,amplifer);
            if(result==null)
                throw new NoneUpdateException();
            return result;
        }

        //根据amplifierID
        @ApiOperation(value = "删除",notes = "删除放大器:根据amplifierId")
        @RequestMapping(value = "/{versionId}",method = RequestMethod.DELETE)
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteByAmpid(@PathVariable Long versionId,@RequestBody List<Long> amplifierId){
            if(amplifierId==null || amplifierId.size()==0)
               throw new NegativeArraySizeException();
            else{
                boolean result=amplifierService.deleteByAmpid(versionId,amplifierId);;
                if(!result)
                    throw new NoneRemoveException();
            }
        }
        //添加放大器 /amplifiers/:versionId/
        @ApiOperation(value = "增加",notes = "增加放大器")
        @RequestMapping(value = "/{versionId}",method = RequestMethod.POST)
        @ResponseStatus(HttpStatus.CREATED)
        public AmplifierDTO insertAmplifier(@PathVariable Long versionId,@RequestBody AmplifierDTO amplifer){
//            amplifer.setVersionId(versionId);
            AmplifierDTO result=amplifierService.insertAmplifier(versionId,amplifer);
            if(result==null)
                throw new NoneSaveException();
            return result;
        }

        //获取所有放大器 /amplifiers/:versionId
        @ApiOperation(value = "查找",notes="查找当前版本下所有放大器")
        @RequestMapping(value = "/{versionId}",method = RequestMethod.GET)
        @ResponseStatus(HttpStatus.OK)
        public List<AmplifierDTO> selectAmplifiers(@PathVariable Long versionId){
            List<AmplifierDTO> result=amplifierService.selectAmplifiers(versionId);
            if(result==null || result.size()<=0)
                throw new NoneGetException();
            return result;
        }
}
