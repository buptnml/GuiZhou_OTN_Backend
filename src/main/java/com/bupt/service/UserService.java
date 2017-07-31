package com.bupt.service;

import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.pojo.UserCreateInfo;

import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * 用户Service层
 */
public interface UserService {
    
    /**
     * 创建用户
     * @param userCreateInfo
     * @return
     */
    UserDTO saveUser(UserCreateInfo userCreateInfo);
    
    /**
     * 批量删除指定id的用户
     * @param idList
     */
    void listRemoveUser(List<Long> idList);
    
    /**
     * 查询用户，根据用户名和密码
     * @param userQuery
     * @return
     */
    UserDTO getUserByUserQuery(UserQuery userQuery);
    
    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    UserDTO getUserByUserId(Long id);
    
    /**
     * 查询所有用户
     * @return
     */
    List<UserDTO> listUser();
    
    /**
     * 更新用户信息
     * @param userCreateInfo
     * @return
     */
    UserDTO updateUser(UserCreateInfo userCreateInfo);
}
