package com.bupt.service.impl;

import com.bupt.dao.SysUserDao;
import com.bupt.dao.SysUserRoleDao;
import com.bupt.entity.SysUser;
import com.bupt.entity.SysUserRole;
import com.bupt.pojo.UserRoleCreateInfo;
import com.bupt.pojo.UserRoleDTO;
import com.bupt.service.UserRoleService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public UserRoleDTO saveUserRole(UserRoleCreateInfo userRoleCreateInfo) {
        if (sysUserRoleDao.insertSelective(this.DTOtoDO(userRoleCreateInfo)) > 0) {
            Example example = getExample(userRoleCreateInfo);
            List<SysUserRole> SysUserRoleList = sysUserRoleDao.selectByExample(example);
            return DOtoDTO(SysUserRoleList.get(0));
        }
        throw new NoneSaveException();
    }

    private Example getExample(UserRoleCreateInfo userRoleCreateInfo) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", userRoleCreateInfo.getRoleName());
        return example;
    }

    @Override
    @Transactional
    public void listRemoveUserRole(List<Long> userRoleIdList) {
        Iterator<Long> idListIterator = userRoleIdList.iterator();
        while (idListIterator.hasNext()) {
            SysUserRole role = sysUserRoleDao.selectByPrimaryKey(idListIterator.next());
            if (sysUserRoleDao.delete(role) == 0) {
                throw new NoneRemoveException();
            } else {
                deleteUserByRole(role);
            }
        }
    }

    @Transactional
    public void deleteUserByRole(SysUserRole role) {
        Example example = getExample(role);
        sysUserDao.deleteByExample(example);

    }

    private Example getExample(SysUserRole role) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userRole", role.getRoleName());
        return example;
    }

    @Override
    public List<UserRoleDTO> listUserRole() {
        Iterator<SysUserRole> sysUserIterator = sysUserRoleDao.selectAll().iterator();
        List<UserRoleDTO> resultList = new ArrayList<>();
        while (sysUserIterator.hasNext()) {
            resultList.add(this.DOtoDTO(sysUserIterator.next()));
        }
        if (resultList.size() == 0 || null == resultList) {
            throw new NoneGetException();
        }
        return resultList;
    }

    UserRoleDTO DOtoDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        UserRoleDTO result = new UserRoleDTO();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }

    SysUserRole DTOtoDO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        SysUserRole result = new SysUserRole();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }

}
