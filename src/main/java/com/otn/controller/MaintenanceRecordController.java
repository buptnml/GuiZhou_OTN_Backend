package com.otn.controller;


import com.otn.controller.util.InputCheckException;
import com.otn.entity.ResMaintenanceRecord;
import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.pojo.MaintenanceRecordQuery;
import com.otn.service.ResMaintenanceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@RestController
@Api(tags = "MaintenanceRecord", description = "检修单相关操作")
@RequestMapping("/maintenanceRecords")
public class MaintenanceRecordController {
    @Resource
    private ResMaintenanceRecordService maintenanceRecordService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(MaintenanceRecordController.class);

    @ApiOperation(value = "增加", notes = "新增检修单（不需要传isDone）")
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    @InputCheckException(reason = "入参允许为null，不需要检查，由函数本身保证入参安全")
    public MaintenanceRecordDTO createLinkType(@RequestBody MaintenanceRecordQuery recordDTO) {
        logger.info(recordDTO.toString());
        return maintenanceRecordService.addRecord(recordDTO);
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    @ApiOperation(value = "查询某个版本下的所有检修单信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<MaintenanceRecordDTO> listNetElement() {
        return maintenanceRecordService.listRecord();
    }

    /**
     * 查询最新记录
     *
     * @return
     */
    @ApiOperation(value = "查询某个版本下的最新检修单信息")
    @RequestMapping(value = "/latestRecord", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResMaintenanceRecord latestNetElement() {
        return maintenanceRecordService.latestRecord();
    }

    @ApiOperation(value = "更新", notes = "修改检修单")
    @RequestMapping(value = "/{maintenanceRecordId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public MaintenanceRecordDTO updateByLinkTypeId(@PathVariable Long maintenanceRecordId) {
        return maintenanceRecordService.updateRecord(maintenanceRecordId);
    }

//    /**
//     * 批量删除, 根据maintenanceRecordId
//     *
//     * @param maintenanceRecordIds
//     */
//    @ApiOperation(value = "删除", notes = "批量删除,根据maintenanceRecordId")
//    @RequestMapping(value = "/{versionId}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteByLinkTypeId( @RequestBody List<Long> maintenanceRecordIds) {
//        if (!maintenanceRecordService.deleteByMaintenanceRecordId(maintenanceRecordIds))
//            throw new NoneRemoveException();
//    }
}
