package com.yoyzi.balance.vo;

import lombok.Data;

@Data
public class PayVo {
    private Integer id;
    private String systemName;
    private String money;
    private String payTime;
    private String payType;
}
