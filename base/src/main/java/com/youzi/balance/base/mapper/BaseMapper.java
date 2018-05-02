package com.youzi.balance.base.mapper;

import com.youzi.balance.base.po.PayPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:17
 **/
public interface BaseMapper<T> {
    int insert(T bean);

    List<T> selectAll();

    T findById(Integer id);

    int update(T bean);

    int queryCount(T bean);

    List<T> selectList(@Param("param") T bean, @Param("limit") Integer limit, @Param("offset")Integer offset);

    Integer selectCount(@Param("param")T bean);
}
