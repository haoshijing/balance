package com.yoyzi.balance.vo;

import lombok.Data;

import java.util.List;

/**
 * @author haoshijing
 * @version 2018年05月23日 19:56
 **/
@Data
public class TotalList {
    private List<PayVo> payVoList;

    private Integer totalAmount;
}
