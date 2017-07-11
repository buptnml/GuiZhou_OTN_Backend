package com.bupt.service;

import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;

import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * 用户Service层
 */
public interface UserService {
    
    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    UserDTO saveUser(UserDTO userDTO);
    
    /**
     * 删除指定id的用户
     * @param idList
     * @return
     */
    boolean listRemoveUser(List<Long> idList);
    
    /**
     * 查询用户，根据用户名和密码
     * @param userQuery
     * @return
     */
    UserDTO getUser(UserQuery userQuery);
    
    /**
     * 查询所有用户
     * @return
     */
    List<UserDTO> listUser();
    
    /**
     * 根据id修改客户信息
     * @param id
     * @param userDTO
     * @return
     */
    UserDTO updateUser(Long id,UserDTO userDTO);
}
