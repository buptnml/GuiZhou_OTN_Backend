package com.otn.controller;


import com.otn.controller.util.InputCheckException;
import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.RecordCreateQuery;
import com.otn.pojo.RecordDTO;
import com.otn.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "Record", description = "检修记录相关操作")
@RequestMapping("/records")
public class RecordController {

    @Resource
    private RecordService recordService;

    @ApiOperation(value = "查询检修记录，支持按条件查询")
    @RequestMapping(value = "/{versionId}/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @VersionCheckException(reason = "加入该注解的目的是为了协助输入参数检查")
    @InputCheckException
    public List<RecordDTO> listRecord(
            @PathVariable Long versionId,
            @RequestParam(value = "target", required = false) String target,
            @RequestParam(value = "startTime", required = false) Date startTime,
            @RequestParam(value = "endTime", required = false) Date endTime) {
        return recordService.listRecords(versionId, target, startTime, endTime);
    }

    @ApiOperation(value = "新增检修记录")
    @RequestMapping(value = "/{versionId}/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @VersionCheckException(reason = "")
    @InputCheckException
    public RecordDTO addRecord(
            @PathVariable Long versionId,
            @RequestBody RecordCreateQuery recordCreateQuery) {
        return recordService.addRecord(versionId, recordCreateQuery);
    }

    @ApiOperation(value = "查看检修记录")
    @RequestMapping(value = "/{id}/{type}/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getRecord(
            @PathVariable Long id,@PathVariable String type) {
        return recordService.getRecord(id,type);
    }
}
