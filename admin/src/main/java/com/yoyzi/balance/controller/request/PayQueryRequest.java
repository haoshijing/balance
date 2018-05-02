package com.yoyzi.balance.controller.request;


import lombok.Data;

@Data
public class PayQueryRequest {

    private Integer page;
    private Integer limit;
    private Integer systemId;
    private Long start;
    private Long end;
}
