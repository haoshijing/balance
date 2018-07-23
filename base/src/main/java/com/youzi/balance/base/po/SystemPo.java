package com.youzi.balance.base.po;

import lombok.Data;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:18
 **/
@Data
public class SystemPo {
    private Integer id;
    private String name;
    private String dbHost;
    private String dbName;
    private String dbUserName;
    private String dbPassword;
    private Integer status;
}
