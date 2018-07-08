package com.otn.service;

import com.otn.pojo.AdminUserDTO;
import com.otn.pojo.BaseUserDTO;
import com.otn.pojo.UserCreateInfo;
import com.otn.pojo.UserQuery;

import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * 用户Service层
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userCreateInfo
     * @return
     */
    AdminUserDTO saveUser(UserCreateInfo userCreateInfo);

    /**
     * 批量删除指定id的用户
     *
     * @param userIdList
     */
    void listRemoveUser(List<Long> userIdList);

    /**
     * 查询用户，根据用户名和密码
     *
     * @param userQuery
     * @return
     */
    AdminUserDTO getUserByUserQuery(UserQuery userQuery);


    /**
     * 查询所有用户
     *
     * @return
     */
    List<AdminUserDTO> listUser();

    /**
     * 查询所有用户
     *
     * @return
     */
    List<BaseUserDTO> listUserById(Long userId);


    /**
     * 更新用户信息（通过ID）
     *
     * @param userId
     * @param userCreateInfo
     * @return
     */
    AdminUserDTO updateUser(Long userId, UserCreateInfo userCreateInfo);


    /**
     * 查询所有用户名
     *
     * @return
     */
    List<String> listUserNames();


    /**
     * 根据ID获取用户
     */
    AdminUserDTO getUserById(Long userId);


}
