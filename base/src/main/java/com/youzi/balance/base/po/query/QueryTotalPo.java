package com.youzi.balance.base.po.query;

import com.youzi.balance.base.po.SystemTotalPo;
import lombok.Data;

@Data
public class QueryTotalPo extends SystemTotalPo {

    private Integer limit;
    private Integer offset;

}
