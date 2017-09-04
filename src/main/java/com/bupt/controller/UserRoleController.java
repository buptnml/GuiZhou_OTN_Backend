package com.bupt.controller;
import com.bupt.pojo.UserRoleCreateInfo;
import com.bupt.pojo.UserRoleDTO;
import com.bupt.service.UserRoleService;
import com.bupt.util.exception.controller.input.NullArgumentException;
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
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @ApiOperation(value = "获取全部角色")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserRoleDTO> listUserRole() {
        List<UserRoleDTO> listSysUserDTO = userRoleService.listUserRole();
        return listSysUserDTO;
    }

    @ApiOperation(value = "创建新角色")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleDTO saveUserRole(@RequestBody UserRoleCreateInfo userRoleCreateInfo) {
        if(userRoleCreateInfo.getRoleName()==null || userRoleCreateInfo.getRoleName().trim().length()==0){
            throw new NullArgumentException("roleName");
        }
        UserRoleDTO UserRoleDTO = this.userRoleService.saveUserRole(userRoleCreateInfo);
        return UserRoleDTO;
    }

    @ApiOperation(value = "批量删除指定角色")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> userRoleIdList) {
        if (null == userRoleIdList || userRoleIdList.size() == 0) {
            throw new IllegalArgumentException("userRoleIdList");
        }
        this.userRoleService.listRemoveUserRole(userRoleIdList);
    }

}
