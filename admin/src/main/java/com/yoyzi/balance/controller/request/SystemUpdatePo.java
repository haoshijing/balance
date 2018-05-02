package com.yoyzi.balance.controller.request;

import lombok.Data;

@Data
public class SystemUpdatePo {
    private String name;
    private String host;
    private String dbUserName;
    private String dbPassword;
    private String dbName;
    private Integer id;
}
