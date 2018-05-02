package com.yoyzi.balance.auth;

import lombok.Data;

@Data
public class AdminAuthInfo {
    private String userName;
    private Integer level;
    private String token;
}
