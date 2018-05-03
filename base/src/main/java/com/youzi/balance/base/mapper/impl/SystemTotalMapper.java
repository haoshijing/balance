package com.youzi.balance.base.mapper.impl;

import com.youzi.balance.base.mapper.BaseMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemTotalPo;
import com.youzi.balance.base.po.query.QueryPayPo;
import com.youzi.balance.base.po.query.QueryTotalPo;

import java.util.List;

public interface SystemTotalMapper extends BaseMapper<SystemTotalPo>{

    List<SystemTotalPo> selectPageList(QueryTotalPo queryTotalPo);


    Integer selectPageCount(QueryTotalPo queryTotalPo);
}
