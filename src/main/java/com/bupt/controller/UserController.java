package com.bupt.controller;


import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
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
    @RequestMapping(value = "/{userName}/{passWord}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable String userName, @PathVariable String passWord) {
        UserQuery userQuery = new UserQuery(userName, passWord);
        this.checkUserQuery(userQuery);
        UserDTO resultDTO = userService.getUser(userQuery);
        if (resultDTO == null) {
            throw new NoneGetException();
        }
        return resultDTO;
    }
    
    @ApiOperation(value = "创建新用户，会忽略掉id信息")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        userDTO.setId(null);
        this.checkUserDTO(userDTO);
        UserDTO resultDTO = this.userService.saveUser(userDTO);
        if (resultDTO == null) {
            throw new NoneSaveException();
        }
        return resultDTO;
    }
    
    
    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        this.checkUserDTO(userDTO);
        UserDTO resultDTO = this.userService.updateUser(id, userDTO);
        if (resultDTO == null) {
            throw new NoneUpdateException();
        }
        return resultDTO;
    }
    
    
    @ApiOperation(value = "批量删除指定id的用户")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> idList) {
        if (null == idList || idList.size() == 0) {
            throw new IllegalArgumentException("idList");
        }
        if (!this.userService.listRemoveUser(idList)) {
            throw new NoneRemoveException();
        }
    }
    
    private void checkUserDTO(UserDTO userDTO) {
        if (userDTO.getPassWord() == null) {
            throw new NullArgumentException("passWord");
        }
        if (userDTO.getUserName() == null) {
            throw new NullArgumentException("userName");
        }
        if (!userDTO.getUserRole().equals("管理员") && !userDTO.getUserRole().equals("普通用户")) {
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
