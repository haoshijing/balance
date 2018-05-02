package com.youzi.balance.base.po;


import lombok.Data;

@Data
public class SystemTotalPo {
    private Integer id;
    private Integer money;
    private Integer systemId;
    private Integer indexAt;
    private String yearStr;
    private Integer typeVal;
}
