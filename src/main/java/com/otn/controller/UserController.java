package com.otn.controller;


import com.otn.controller.util.VersionCheckException;
import com.otn.pojo.UserCreateInfo;
import com.otn.pojo.UserDTO;
import com.otn.pojo.UserQuery;
import com.otn.pojo.UserRoleDTO;
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
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listUserByName(@PathVariable String userName) {
        return userService.listUserByName(userName);
    }

    @ApiOperation(value = "获取该用户能查到的所有用户")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listUserByName() {
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
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRole(userRole);
        return userRoleDTOList.size() <= 0;
    }

}
