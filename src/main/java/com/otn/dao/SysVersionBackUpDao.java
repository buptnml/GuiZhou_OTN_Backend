package com.otn.dao;

import com.otn.entity.SysBackUp;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 版本备份Dao层
 */
@Repository
public interface SysVersionBackUpDao extends Mapper<SysBackUp> {
}
