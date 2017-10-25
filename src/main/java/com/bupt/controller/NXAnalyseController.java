package com.bupt.controller;

import com.alibaba.druid.filter.AutoLoad;
import com.bupt.pojo.NXAnalyseItemDTO;
import com.bupt.service.NXAnalyseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhangminchao on 2017/10/23.
 */
@RestController
@Api(tags = "N-X", description = "N-X分析")
@RequestMapping("/nx")
public class NXAnalyseController {


    @Autowired
    private NXAnalyseService nxAnalyseService;


    private static final int EQUIP = 0;
    private static final int LINK = 1;
    private static final int BOTH = 2;


    @RequestMapping(value = "/analyse",method = RequestMethod.GET)
    @ApiOperation(value = "设备N-X分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionId",value="版本id",required = true,paramType = "query",dataType = "long"),
            @ApiImplicitParam(name = "num",value="故障数量",required = true,paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "type",value="故障种类",required = true,paramType = "query",dataType = "int")
    })
    public List<NXAnalyseItemDTO> analyseEquip( long versionId, int num, int type) {

        if (type == EQUIP)
            return nxAnalyseService.analyseEquip(versionId, num);

        else if (type == LINK)
            return nxAnalyseService.analyseLink(versionId, num);

        else
            return nxAnalyseService.analyseEquipAndLink(versionId,num);
    }

}
