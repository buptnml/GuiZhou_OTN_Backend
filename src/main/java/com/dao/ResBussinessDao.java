package com.dao;

import com.entity.ResBussiness;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 光通道（业务）层的Dao接口
 */
@Repository
public interface ResBussinessDao extends Mapper<ResBussiness> {
}
