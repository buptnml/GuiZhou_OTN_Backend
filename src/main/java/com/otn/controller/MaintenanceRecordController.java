package com.otn.controller;


import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.service.ResMaintenanceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "MaintenanceRecord", description = "检修单相关操作")
@RequestMapping("/maintenanceRecords")
public class MaintenanceRecordController {
    @Resource
    private ResMaintenanceRecordService maintenanceRecordService;

    @ApiOperation(value = "增加", notes = "新增检修单")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceRecordDTO createLinkType(@RequestBody MaintenanceRecordDTO recordDTO) {
        return maintenanceRecordService.addRecord(recordDTO);
    }

}
