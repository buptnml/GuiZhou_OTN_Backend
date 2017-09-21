package com.bupt.dao;

import com.bupt.entity.ResDisk;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ResDiskDao extends Mapper<ResDisk> {
}
