package com.bupt.dao;

import com.bupt.entity.SysVersion;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 版本管理Dao层
 */
@Repository
public interface SysVersionDao extends Mapper<SysVersion> {
}
