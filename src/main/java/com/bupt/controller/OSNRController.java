package com.bupt.controller;

import com.bupt.controller.utils.VersionCheckException;
import com.bupt.facade.OSNRService;
import com.bupt.pojo.BussinessDTO;
import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.ResultOSNRDetail;
import com.bupt.pojo.RouteOSNRDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 光通道（业务）层的Controller层
 */
@RestController
@VersionCheckException(reason = "OSNR相关操作不会对数据库写数据，不需要进行版本检查")
@Api(tags = "OSNR", description = "OSNR相关操作")
@RequestMapping(value = "/osnrs")
public class OSNRController {
    @Resource
    private OSNRService osnrService;

    @ApiOperation(value = "查询某个版本下的所有不合格的光通道信息")
    @RequestMapping(value = "/{versionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BussinessDTO> listErrorBussiness(@PathVariable Long versionId) {
        return osnrService.listErrorBussiness(versionId);
    }

    @ApiOperation(value = "查询某个版本下的某光通道中所有可以计算出的节点的功率数据")
    @RequestMapping(value = "/nodes/{versionId}/{bussinessId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)

    public List<NodeOSNRDetail> getNodeOSNRDetail(@PathVariable Long versionId, @PathVariable Long bussinessId) {
        return osnrService.getNodeOSNRDetail(versionId, bussinessId);
    }

    @ApiOperation(value = "查询某个版本下的某光通道大致报表信息")
    @RequestMapping(value = "/routes/{versionId}/{bussinessId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RouteOSNRDetail> getRouteOSNRDetail(@PathVariable Long versionId, @PathVariable Long bussinessId) {
        return osnrService.getRouteOSNRDetail(versionId, bussinessId);
    }

    @ApiOperation(value = "查询某个版本下的某光通道某路由OSNR计算值")
    @RequestMapping(value = "/results/{versionId}/{bussinessId}/{isMain}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ResultOSNRDetail> getOSNRResult(@PathVariable Long versionId, @PathVariable Long
            bussinessId, @PathVariable Boolean isMain) {
        return osnrService.getOSNRResult(versionId, bussinessId, isMain);
    }
}
