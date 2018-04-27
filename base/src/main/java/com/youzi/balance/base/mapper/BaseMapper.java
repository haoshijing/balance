package com.youzi.balance.base.mapper;

import java.util.List;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:17
 **/
public interface BaseMapper<T> {
    int insert(T bean);

    List<T> selectAll();
}
