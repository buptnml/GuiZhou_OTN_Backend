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
    boolean saveUser(UserDTO userDTO);
    
    /**
     * 删除指定id的用户
     * @param idList
     * @return
     */
    boolean ListRemoveUser(List<Long> idList);
    
    /**
     * 查询用户
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
     * 修改用户
     * @param userDTO
     * @return
     */
    boolean updateUser(UserDTO userDTO);
}
