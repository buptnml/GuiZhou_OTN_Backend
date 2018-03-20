package com.otn.service.impl;

import com.otn.dao.SysPermissionDao;
import com.otn.entity.SysPermission;
import com.otn.pojo.PermissionDTO;
import com.otn.service.PermissonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("PermissionService")
public class PermissionServiceImpl implements PermissonService {
    @Resource
    SysPermissionDao sysPermissionDao;

    @Override
    public List<PermissionDTO> listPermissions() {
        return sysPermissionDao.selectAll().stream().map(this::convertDTO).collect(Collectors.toList());
    }

    private PermissionDTO convertDTO(SysPermission obj) {
        PermissionDTO res = new PermissionDTO();
        BeanUtils.copyProperties(obj, res);
        return res;
    }
}
