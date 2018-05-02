package com.youzi.balance.base.mapper.impl;

import com.youzi.balance.base.mapper.BaseMapper;
import com.youzi.balance.base.po.AdminPo;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<AdminPo> {

    AdminPo selectByUsername(String userName);

    int updatePwd(@Param("newPwd") String newPwd, @Param("userName") String userName);
}
