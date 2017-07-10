package com.bupt.dao;

import com.bupt.entity.SysUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * 用户Dao层
 */
@Repository
public interface SysUserDao extends Mapper<SysUser> {
}
