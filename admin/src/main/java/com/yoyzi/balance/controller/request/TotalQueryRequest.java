package com.yoyzi.balance.controller.request;

import lombok.Data;

@Data
public class TotalQueryRequest {
    private Integer page;
    private Integer limit;
    private Integer systemId;
    private Integer index;
    private Integer type;
    private String year;
}
