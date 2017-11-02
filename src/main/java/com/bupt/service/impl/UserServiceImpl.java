package com.bupt.service.impl;

import com.bupt.dao.SysUserDao;
import com.bupt.entity.SysUser;
import com.bupt.pojo.UserCreateInfo;
import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
@Service("userService")
class UserServiceImpl implements UserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public UserDTO saveUser(UserCreateInfo userCreateInfo) {
        if (sysUserDao.insertSelective(this.convertToSysUser(userCreateInfo)) > 0) {
            return this.getUserByUserQuery(new UserQuery(userCreateInfo.getUserName(), userCreateInfo.getPassword()));
        }
        throw new NoneSaveException();
    }

    @Override
    @Transactional(rollbackFor = NoneRemoveException.class)
    public void listRemoveUser(List<Long> idList) {
        for (Long anIdList : idList) {
            if (sysUserDao.deleteByPrimaryKey(anIdList) == 0) {
                throw new NoneRemoveException();
            }
        }
    }


    @Override
    public UserDTO getUserByUserQuery(UserQuery userQuery) {
        List<SysUser> sysUserList = sysUserDao.selectByExample(getExample(userQuery));
        if (sysUserList.size() == 0 || sysUserList.size() > 1) {
            throw new NoneGetException();
        }
        return this.convertToUserDTO(sysUserList.get(0));
    }

    private Example getExample(UserQuery userQuery) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userQuery.getUserName());
        criteria.andEqualTo("password", userQuery.getPassword());
        return example;
    }

    @Override
    public UserDTO getUserByUserId(Long userId) {
        UserDTO userDTO = this.convertToUserDTO(sysUserDao.selectByPrimaryKey(userId));
        if (null == userDTO) {
            throw new NoneGetException();
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> listUser() {
        List<UserDTO> resultList = sysUserDao.selectAll().stream().sorted(Comparator.comparing
                (SysUser::getGmtModified).reversed()).map(this::convertToUserDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到用户相关记录！");
        }
        return resultList;
    }

    @Override
    public UserDTO updateUser(UserCreateInfo userCreateInfo) {
        if (sysUserDao.updateByExampleSelective(this.convertToSysUser(userCreateInfo), getExample(userCreateInfo
                .getUserName())) >
                0) {
            return this.getUserByUserQuery(new UserQuery(userCreateInfo.getUserName(), userCreateInfo.getPassword()));
        }
        throw new NoneUpdateException();
    }

    private Example getExample(String userName) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", userName);
        return example;
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

    @Override
    public List<String> listUserNames() {
        List<UserDTO> userDTOS = listUser();
        List<String> userNameList = new ArrayList<>();
        for (UserDTO userDTO : userDTOS) {
            userNameList.add(userDTO.getUserName());
        }
        return userNameList;
    }

    @Override
    public UserDTO getUserByName(String userName) {
        List<SysUser> results = sysUserDao.selectByExample(getExample(userName));
        if (results.size() == 0) {
            throw new NoneGetException("userName/creatorName");
        }
        return convertToUserDTO(results.get(0));
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
