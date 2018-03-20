package com.otn.dao;

import com.otn.entity.ResDisk;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ResDiskDao extends Mapper<ResDisk> {
}
