package com.otn.dao;

import com.otn.entity.SysSyncLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysSyncLogDao extends Mapper<SysSyncLog> {
}
