package com.bupt.service.impl;

import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.service.UserService;

import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class UserServiceImpl implements UserService {
    @Override
    public boolean saveUser(UserDTO userDTO) {
        return false;
    }
    
    @Override
    public boolean removeUser(UserQuery userQuery) {
        return false;
    }
    
    @Override
    public UserDTO getUser(UserQuery userQuery) {
        return null;
    }
    
    @Override
    public List<UserDTO> listUser() {
        return null;
    }
    
    @Override
    public boolean updateUser(UserDTO userDTO) {
        return false;
    }
}
