package com.otn.controller;


import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.*;
import com.otn.service.UserRoleService;
import com.otn.service.UserService;
import com.otn.util.exception.controller.input.IllegalArgumentException;
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

    @ApiOperation(value = "获取该用户能查到的所有用户")
    @RequestMapping(value = "/{operatorId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BaseUserDTO> listUserByName(@PathVariable Long operatorId) {
        List<BaseUserDTO> res = userService.listUserById(operatorId);
        return res;
    }

//    @ApiOperation(value = "获取该用户能查到的所有用户")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserDTO> listUserByName() {
//        return userService.listUser();
//    }


    @ApiOperation(value = "按条件查询用户")
    @RequestMapping(value = "/{userName}/{password}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BaseUserDTO getUserByUserQuery(@PathVariable String userName, @PathVariable String password) {
        return userService.getUserByUserQuery(new UserQuery(userName, password));
    }


    @ApiOperation(value = "创建新用户")
    @RequestMapping(value = "/{operatorId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserDTO saveUserByName(@PathVariable Long operatorId, @RequestBody UserCreateInfo userCreateInfo) {
        if (!userService.getUserById(operatorId).getUserRole().equals("管理员"))
            throw new IllegalArgumentException("非管理员不允许新增用户信息！");
        this.checkUserDTO(new AdminUserDTO(null, userCreateInfo.getUserName(), userCreateInfo.getPassword(),
                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
        return userService.saveUser(userCreateInfo);
    }

//    @ApiOperation(value = "创建新用户")
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDTO saveUser(@RequestBody UserCreateInfo userCreateInfo) {
//        this.checkUserDTO(new UserDTO(null, userCreateInfo.getUserName(), userCreateInfo.getPassword(),
//                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
//        return userService.saveUser(userCreateInfo);
//    }


    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/{operatorId}/{userId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserDTO updateUser(@PathVariable Long operatorId, @PathVariable Long userId, @RequestBody UserCreateInfo
            userCreateInfo) {
        AdminUserDTO info = userService.getUserById(operatorId);
        if (operatorId.equals(userId) || info.getUserRole().equals("管理员")) {
            this.checkUserDTO(new AdminUserDTO(userId, userCreateInfo.getUserName(), userCreateInfo.getPassword(),
                    userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
            return userService.updateUser(userId, userCreateInfo);
        }
        throw new IllegalArgumentException("普通用户只允许修改自己的用户信息！");
    }

    @ApiOperation(value = "批量删除指定id的用户")
    @RequestMapping(value = "/{operatorId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUserByName(@PathVariable Long operatorId, @RequestBody List<Long> idList) {
        if (userService.getUserById(operatorId).getUserRole().equals("管理员")) {
            if (idList.contains(operatorId)) throw new IllegalArgumentException("不允许删除自己!");
            userService.listRemoveUser(idList);
            return;
        }
        throw new IllegalArgumentException("非管理员不允许进行删除操作！");
    }

//    @ApiOperation(value = "批量删除指定id的用户")
//    @RequestMapping(value = "/", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void listRemoveUser(@RequestBody List<Long> idList) {
//        userService.listRemoveUser(idList);
//    }


    private void checkUserDTO(BaseUserDTO userDTO) {
        if (checkUserRole(userDTO.getUserRole())) {
            throw new IllegalArgumentException("userRole");
        }
    }

    private boolean checkUserRole(String userRole) {
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRole(userRole);
        return userRoleDTOList.size() <= 0;
    }

}
