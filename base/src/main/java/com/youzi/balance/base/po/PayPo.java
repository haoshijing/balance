package com.youzi.balance.base.po;

import lombok.Data;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:19
 **/
@Data
public class PayPo {
    private Integer id;
    private Integer systemId;
    private Integer money;
    private Long insertTime;
    private Long payTime;
    private Long syncTime;
}
