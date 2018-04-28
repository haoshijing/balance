package com.youzi.balance.base.mapper.impl;

import com.youzi.balance.base.mapper.BaseMapper;
import com.youzi.balance.base.po.PayPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:21
 **/
public interface PayMapper extends BaseMapper<PayPo> {

    Integer sumMoney(@Param("systemId") Integer systemId, @Param("start") Long start,  @Param("end")Long end);
}
