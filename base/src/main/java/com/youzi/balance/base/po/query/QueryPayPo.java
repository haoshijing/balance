package com.youzi.balance.base.po.query;

import com.youzi.balance.base.po.PayPo;
import lombok.Data;

@Data
public class QueryPayPo extends PayPo {
    private Long start;
    private Long end;
    private Integer limit;
    private Integer offset;
}
