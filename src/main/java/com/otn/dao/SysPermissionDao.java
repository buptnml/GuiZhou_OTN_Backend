package com.otn.dao;

import com.otn.entity.SysPermission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysPermissionDao extends Mapper<SysPermission> {
}
