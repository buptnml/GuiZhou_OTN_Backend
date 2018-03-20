package com.otn.dao;

import com.otn.entity.SysRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysRoleDao extends Mapper<SysRole> {
}
