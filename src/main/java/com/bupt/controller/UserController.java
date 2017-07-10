package com.bupt.controller;


import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneSaveException;
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
public class UserController {
    @Resource
    private UserService userService;
    
    @ApiOperation(value = "查询全部用户信息")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> listUser() {
        List<UserDTO> listSysUserDTO = userService.listUser();
        if (listSysUserDTO.size() == 0) {
            throw new NoneGetException();
        }
        return listSysUserDTO;
    }
    
    
    @ApiOperation(value = "按条件查询用户")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@RequestBody UserQuery userQuery) {
        this.checkUserQuery(userQuery);
        UserDTO sysUserDTO = userService.getUser(userQuery);
        if (sysUserDTO == null) {
            throw new NoneGetException();
        }
        return sysUserDTO;
    }
    
    @ApiOperation(value = "创建新用户，会忽略掉id信息")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody UserDTO sysUserDTO) {
        sysUserDTO.setId(null);
        this.checkUserDTO(sysUserDTO);
        if (!this.userService.saveUser(sysUserDTO)) {
            throw new NoneSaveException();
        }
        return this.userService.getUser(new UserQuery(sysUserDTO.getUserName(),sysUserDTO.getPassWord()));
    }
    
    private void checkUserDTO(UserDTO userDTO) {
        if (userDTO.getPassWord() == null) {
            throw new NullArgumentException("passWord");
        }
        if (userDTO.getUserName() == null) {
            throw new NullArgumentException("userName");
        }
        if (userDTO.getUserRole() != "管理员" && userDTO.getUserRole() != "普通用户"){
            throw new IllegalArgumentException("userRole");
        }
    }
    
    private void checkUserQuery(UserQuery userQuery) {
        if (userQuery.getPassWord() == null) {
            throw new NullArgumentException("passWord");
        }
        if (userQuery.getUserName() == null) {
            throw new NullArgumentException("userName");
        }
    }
    
}
