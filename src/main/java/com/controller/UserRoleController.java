package com.controller;

import com.controller.util.VersionCheckException;
import com.pojo.UserRoleCreateInfo;
import com.pojo.UserRoleDTO;
import com.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理的Controller层
 */
@RestController
@Api(tags = "UserRole", description = "角色相关操作")
@RequestMapping(value = "/userRoles")
@VersionCheckException(reason = "角色相关操作不涉及版本检查")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @ApiOperation(value = "获取全部角色")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserRoleDTO> listUserRole() {
        return userRoleService.listUserRole();
    }

    @ApiOperation(value = "创建新角色")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleDTO saveUserRole(@RequestBody UserRoleCreateInfo userRoleCreateInfo) {
        return this.userRoleService.saveUserRole(userRoleCreateInfo);
    }

    @ApiOperation(value = "批量删除指定角色")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUserRole(@RequestBody List<Long> userRoleIdList) {
        this.userRoleService.listRemoveUserRole(userRoleIdList);
    }

}
