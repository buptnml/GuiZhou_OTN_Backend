package com.bupt.service.impl;

import com.bupt.dao.SysUserDao;
import com.bupt.entity.SysUser;
import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Resource
    private SysUserDao sysUserDao;
    
    @Override
    public boolean saveUser(UserDTO userDTO) {
        userDTO.setId(null);
        if (sysUserDao.insertSelective(this.convertToSysUser(userDTO)) > 0) {
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(rollbackFor=NoneRemoveException.class)
    public boolean ListRemoveUser(List<Long> idList) {
        Iterator<Long> idListIterator=idList.iterator();
        while(idListIterator.hasNext()){
            if (sysUserDao.deleteByPrimaryKey(idListIterator.hasNext()) == 0) {
                throw new NoneRemoveException();
            }
        }
        return true;
    }
    
    
    @Override
    public UserDTO getUser(UserQuery userQuery) {
        Example example = getUserQueryExample(userQuery);
        List<SysUser> sysUserList = sysUserDao.selectByExample(example);
        if (sysUserList.size() == 0 || sysUserList.size() > 1) {
            return null;
        }
        return this.convertToUserDTO(sysUserList.get(0));
    }
    
    @Override
    public List<UserDTO> listUser() {
        Iterator<SysUser> sysUserIterator = sysUserDao.selectAll().iterator();
        List<UserDTO> resultList = new ArrayList<>();
        while (sysUserIterator.hasNext()) {
            resultList.add(this.convertToUserDTO(sysUserIterator.next()));
        }
        return resultList;
    }
    
    @Override
    public boolean updateUser(UserDTO userDTO) {
        Example example = new Example(SysUser.class);
        userDTO.setId(null);
        if (sysUserDao.updateByExampleSelective(this.convertToSysUser(userDTO), example) > 0) {
            return true;
        }
        return false;
    }
    
    private Example getUserQueryExample(UserQuery userQuery) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userQuery.getUserName());
        criteria.andEqualTo("passWord", userQuery.getPassWord());
        return example;
    }
    
    private UserDTO convertToUserDTO(SysUser sysUser) {
        if (null == sysUser) {
            return null;
        }
        UserDTO resultDTO = new UserDTO();
        BeanUtils.copyProperties(sysUser, resultDTO);
        return resultDTO;
    }
    
    private SysUser convertToSysUser(UserDTO userDTO) {
        if (null == userDTO) {
            return null;
        }
        SysUser result = new SysUser();
        BeanUtils.copyProperties(userDTO, result);
        return result;
    }
}
