package com.bupt.controller;


import com.bupt.pojo.UserCreateInfo;
import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
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
        return listSysUserDTO;
    }
    
    
    @ApiOperation(value = "按条件查询用户")
    @RequestMapping(value = "/{userName}/{passWord}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByUserQuery(@PathVariable String userName, @PathVariable String passWord) {
        UserQuery userQuery = new UserQuery(userName, passWord);
        this.checkUserQuery(userQuery);
        UserDTO resultDTO = userService.getUserByUserQuery(userQuery);
        return resultDTO;
    }
    
    
    @ApiOperation(value = "创建新用户")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody UserCreateInfo userCreateInfo) {
        this.checkUserDTO(new UserDTO(null, userCreateInfo.getUserName(), userCreateInfo.getPassWord(),
                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
        return userService.saveUser(userCreateInfo);
    }
    
    
    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO updateUser(@PathVariable Long userId,@RequestBody UserCreateInfo userCreateInfo) {
        
        this.checkUserDTO(new UserDTO(null, userCreateInfo.getUserName(), userCreateInfo.getPassWord(),
                userCreateInfo.getUserRole(), userCreateInfo.getUserGroup()));
        UserDTO resultDTO = this.userService.updateUser(userId,userCreateInfo);
        return resultDTO;
    }
    
    
    @ApiOperation(value = "批量删除指定id的用户")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listRemoveUser(@RequestBody List<Long> idList) {
        if (null == idList || idList.size() == 0) {
            throw new IllegalArgumentException("idList");
        }
        this.userService.listRemoveUser(idList);
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
