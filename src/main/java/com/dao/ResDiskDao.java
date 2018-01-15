package com.dao;

import com.entity.ResDisk;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ResDiskDao extends Mapper<ResDisk> {
}
