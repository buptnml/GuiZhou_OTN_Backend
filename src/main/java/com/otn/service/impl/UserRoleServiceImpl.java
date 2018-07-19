package com.otn.service.impl;

import com.otn.dao.SysRoleDao;
import com.otn.entity.SysRole;
import com.otn.pojo.UserRoleDTO;
import com.otn.service.UserRoleService;
import com.otn.util.exception.controller.result.NoneGetException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("userRoleService")
class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

//    @Override
//    public UserRoleDTO saveUserRole(UserRoleCreateInfo userRoleCreateInfo) {
//        if (sysRoleDao.insertSelective(this.DTOtoDO(userRoleCreateInfo)) > 0) {
//            Example example = getExample(userRoleCreateInfo);
//            List<SysUserRole> SysUserRoleList = sysRoleDao.selectByExample(example);
//            return DOtoDTO(SysUserRoleList.get(0));
//        }
//        throw new NoneSaveException();
//    }

//    private Example getExample(UserRoleCreateInfo userRoleCreateInfo) {
//        Example example = new Example(SysUserRole.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("roleName", userRoleCreateInfo.getRoleName());
//        return example;
//    }

//    @Override
//    @Transactional
//    public void listRemoveUserRole(List<Long> userRoleIdList) {
//        for (Long anUserRoleIdList : userRoleIdList) {
//            SysUserRole role = sysRoleDao.selectByPrimaryKey(anUserRoleIdList);
//            if (sysRoleDao.delete(role) == 0) {
//                throw new NoneRemoveException();
//            } else {
//                deleteUserByRole(role);
//            }
//        }
//    }

//    @Transactional
//    public void deleteUserByRole(SysUserRole role) {
//        Example example = getExample(role);
//        sysUserDao.deleteByExample(example);
//
//    }

    private Example getExample(String roleName) {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName);
        return example;
    }

    @Override
    public List<UserRoleDTO> listUserRole(String roleName) {
        List roleList = sysRoleDao.selectByExample(getExample(roleName));
        if (roleList.size() == 0) throw new IllegalArgumentException("输入的用户角色不合法");
        SysRole yourRole = sysRoleDao.selectByExample(getExample(roleName)).get(0);
        List<UserRoleDTO> resultList = sysRoleDao.selectAll().stream().filter(role -> role.getRoleId() >= yourRole.getRoleId()).map
                (this::DOtoDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到用户角色相关记录");
        }
        return resultList;
    }

    private UserRoleDTO DOtoDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        UserRoleDTO result = new UserRoleDTO();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }

    private SysRole DTOtoDO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        SysRole result = new SysRole();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }

}
