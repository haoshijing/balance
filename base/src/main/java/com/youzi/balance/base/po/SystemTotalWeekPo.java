package com.youzi.balance.base.po;


import lombok.Data;

@Data
public class SystemTotalWeekPo {
    private Integer id;
    private Integer money;
    private Integer systemId;
    private Integer week;
    private String year;
}
