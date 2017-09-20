package com.bupt.dao;

import com.bupt.entity.ResLink;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

/**
 * 链路
 */
@Repository
public interface ResLinkDao extends Mapper<ResLink>{
}
