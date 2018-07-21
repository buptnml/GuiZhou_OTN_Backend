package com.otn.service.impl;

import com.otn.dao.SysRoleDao;
import com.otn.dao.SysUserDao;
import com.otn.entity.SysRole;
import com.otn.entity.SysUser;
import com.otn.pojo.AdminUserDTO;
import com.otn.pojo.BaseUserDTO;
import com.otn.pojo.UserCreateInfo;
import com.otn.pojo.UserQuery;
import com.otn.service.UserService;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import com.otn.util.exception.controller.result.NoneUpdateException;
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
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public AdminUserDTO saveUser(UserCreateInfo userCreateInfo) {
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
    public AdminUserDTO getUserByUserQuery(UserQuery userQuery) {
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
    public List<AdminUserDTO> listUser() {
        List<AdminUserDTO> resultList = sysUserDao.selectAll().stream().sorted(Comparator.comparing
                (SysUser::getGmtModified).reversed()).map(this::convertToUserDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到用户相关记录！");
        }
        return resultList;
    }

    @Override
    public List<BaseUserDTO> listUserById(Long userId) {
        AdminUserDTO info = getUserById(userId);
        List<SysRole> yourRoleList = sysRoleDao.selectByExample(getRoleExample(info.getUserRole()));
        List<String> resultList = sysRoleDao.selectAll().stream().
                filter(role -> role.getRoleId() >= yourRoleList.get(0).getRoleId()).map(SysRole::getRoleName).collect(Collectors.toList());
        if (info.getUserRole().equals("管理员")) {
            return listUser().stream().filter(user -> resultList.contains(user.getUserRole())).collect(Collectors.toList());
        } else {
            return listUser().stream().filter(user -> resultList.contains(user.getUserRole())).map(user -> {
                if (user.getUserId() != userId) user.setPassword("******");
                return user;
            }).collect(Collectors.toList());
        }
    }

    private Example getRoleExample(String roleName) {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName);
        return example;
    }



    @Override
    public AdminUserDTO updateUser(Long userId, UserCreateInfo userCreateInfo) {
        SysUser DO = convertToSysUser(userCreateInfo);
        DO.setUserId(userId);
        if (sysUserDao.updateByPrimaryKeySelective(DO) > 0) {
            return convertToUserDTO(sysUserDao.selectByPrimaryKey(userId));
        }
        throw new NoneUpdateException();
    }

    @Override
    public List<String> listUserNames() {
        List<AdminUserDTO> userDTOS = listUser();
        List<String> userNameList = new ArrayList<>();
        for (AdminUserDTO userDTO : userDTOS) {
            userNameList.add(userDTO.getUserName());
        }
        return userNameList;
    }

    @Override
    public AdminUserDTO getUserById(Long userId) {
        AdminUserDTO resultDTO = convertToUserDTO(sysUserDao.selectByPrimaryKey(userId));
        if (resultDTO == null) throw new IllegalArgumentException("找不到操作员的身份");
        return resultDTO;
    }


    private AdminUserDTO convertToUserDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        AdminUserDTO resultDTO = new AdminUserDTO();
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
