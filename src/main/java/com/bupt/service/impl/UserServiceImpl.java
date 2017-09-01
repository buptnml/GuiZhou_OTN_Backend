package com.bupt.service.impl;

import com.bupt.dao.SysUserDao;
import com.bupt.entity.SysUser;
import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import com.bupt.pojo.UserCreateInfo;
import com.bupt.service.UserService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
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
    public UserDTO saveUser(UserCreateInfo userCreateInfo) {
        if (sysUserDao.insertSelective(this.convertToSysUser(userCreateInfo)) > 0) {
            return this.getUserByUserQuery(new UserQuery(userCreateInfo.getUserName(), userCreateInfo.getPassWord()));
        }
        throw new NoneSaveException();
    }
    
    @Override
    @Transactional(rollbackFor=NoneRemoveException.class)
    public void listRemoveUser(List<Long> idList) {
        Iterator<Long> idListIterator=idList.iterator();
        while(idListIterator.hasNext()){
            if (sysUserDao.deleteByPrimaryKey(idListIterator.next()) == 0) {
                throw new NoneRemoveException();
            }
        }
    }
    
    
    
    @Override
    public UserDTO getUserByUserQuery(UserQuery userQuery) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userQuery.getUserName());
        criteria.andEqualTo("passWord", userQuery.getPassWord());
        List<SysUser> sysUserList = sysUserDao.selectByExample(example);
        if (sysUserList.size() == 0 || sysUserList.size() > 1) {
            throw new NoneGetException();
        }
        return this.convertToUserDTO(sysUserList.get(0));
    }
    
    @Override
    public UserDTO getUserByUserId(Long userId) {
        UserDTO userDTO= this.convertToUserDTO(sysUserDao.selectByPrimaryKey(userId));
        if(null==userDTO){
            throw new NoneGetException();
        }
        return userDTO;
    }
    
    @Override
    public List<UserDTO> listUser() {
        Iterator<SysUser> sysUserIterator = sysUserDao.selectAll().iterator();
        List<UserDTO> resultList = new ArrayList<>();
        while (sysUserIterator.hasNext()) {
            resultList.add(this.convertToUserDTO(sysUserIterator.next()));
        }
        if(resultList.size()==0||null==resultList){
            throw new NoneGetException();
        }
        return resultList;
    }
    
    @Override
    public UserDTO updateUser(UserCreateInfo userCreateInfo) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userCreateInfo.getUserName());
        if (sysUserDao.updateByExampleSelective(this.convertToSysUser(userCreateInfo),example) > 0) {
            return this.getUserByUserQuery(new UserQuery(userCreateInfo.getUserName(), userCreateInfo.getPassWord()));
        }
        throw new NoneUpdateException();
    }

    @Override
    public UserDTO updateUser(Long userId, UserCreateInfo userCreateInfo) {
        SysUser DO = convertToSysUser(userCreateInfo);
        DO.setUserId(userId);
        if (sysUserDao.updateByPrimaryKeySelective(DO) > 0) {
            return convertToUserDTO(sysUserDao.selectByPrimaryKey(userId));
        }
        throw new NoneUpdateException();
    }


    private UserDTO convertToUserDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        UserDTO resultDTO = new UserDTO();
        BeanUtils.copyProperties(inputObject, resultDTO);
        return resultDTO;
    }
    
    private SysUser convertToSysUser(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        SysUser result = new SysUser();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }
}
