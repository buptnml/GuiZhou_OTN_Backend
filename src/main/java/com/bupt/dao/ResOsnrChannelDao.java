package com.bupt.dao;

import com.bupt.entity.ResOsnrChannel;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.example.UpdateByExampleSelectiveMapper;

@Repository
public interface ResOsnrChannelDao extends Mapper<ResOsnrChannel> {
}
