package com.bupt.controller;


import com.bupt.controller.utils.VersionCheckException;
import com.bupt.pojo.UserCreateInfo;
import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.pojo.UserRoleDTO;
import com.bupt.service.UserRoleService;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * 用户操作的Controller层
 */
@RestController
@Api(tags = "User", description = "用户相关操作")
@RequestMapping(value = "/users")
@VersionCheckException(reason = "用户操作不涉及版本，不需要进行版本检查")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @ApiOperation(value = "查询全部用户信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listUser() {
        return userService.listUser();
    }


    @ApiOperation(value = "按条件查询用户")
    @RequestMapping(value = "/{userName}/{password}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByUserQuery(@PathVariable String userName, @PathVariable String password) {
        return userService.getUserByUserQuery(new UserQuery(userName, password));
    }


    @ApiOperation(value = "创建新用户")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody UserCreateInfo userCreateInfo) {
        this.checkUserDTO(new UserDTO(null, userCreateInfo.getUserName(), userCreateInfo.getPassword(),
                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
        return userService.saveUser(userCreateInfo);
    }


    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserCreateInfo userCreateInfo) {
        this.checkUserDTO(new UserDTO(userId, userCreateInfo.getUserName(), userCreateInfo.getPassword(),
                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
        return this.userService.updateUser(userId, userCreateInfo);
    }


    @ApiOperation(value = "批量删除指定id的用户")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> idList) {
        this.userService.listRemoveUser(idList);
    }

    private void checkUserDTO(UserDTO userDTO) {
        if (checkUserRole(userDTO.getUserRole())) {
            throw new IllegalArgumentException("userRole");
        }
    }

    private boolean checkUserRole(String userRole) {
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRole();
        for (UserRoleDTO role : userRoleDTOList) {
            if (role.getRoleName().equals(userRole)) {
                return false;
            }
        }
        return true;
    }


}
