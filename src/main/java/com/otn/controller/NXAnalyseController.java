package com.otn.controller;

import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.NXAnalyseItemDTO;
import com.otn.service.NXAnalyseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangminchao on 2017/10/23.
 */
@RestController
@Api(tags = "N-X", description = "N-X分析")
@RequestMapping("/nx")
@VersionCheckException(reason = "N-X分析相关操作不会对数据库写数据，不需要进行版本检查")
public class NXAnalyseController {


    private static final int EQUIP = 0;
    private static final int LINK = 1;
    private static final int BOTH = 2;
    @Resource
    private NXAnalyseService nxAnalyseService;

    @RequestMapping(value = "/analyse", method = RequestMethod.GET)
    @ApiOperation(value = "设备N-X分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionId", value = "版本id", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "num", value = "故障数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "故障种类", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "circleId", value = "环路", required = true, paramType = "query", dataType =
                    "String")
    })
    public List<NXAnalyseItemDTO> analyseEquip(long versionId, int num, int type, String circleId) {
        if (type == EQUIP)
            return nxAnalyseService.analyseEquip(versionId, num, circleId);
        if (type == LINK)
            return nxAnalyseService.analyseLink(versionId, num, circleId);
        if (type == BOTH)
            return nxAnalyseService.analyseEquipAndLink(versionId, num, circleId);
        else {
            throw new IllegalArgumentException("类型参数出错！");
        }
    }

}
