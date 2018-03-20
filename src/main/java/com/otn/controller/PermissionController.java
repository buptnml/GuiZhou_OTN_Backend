package com.otn.controller;

import com.otn.pojo.PermissionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "permission", description = "权限相关操作")
@RequestMapping("/permissions")
public class PermissionController {

    @ApiOperation(value = "获取现有的所有的权限列表")
    public List<PermissionDTO> listPermission() {
        return null;
    }

    public List<PermissionDTO> updatePermissionList(
    ) {
        return null;
    }

}
