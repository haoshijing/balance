package com.yoyzi.balance.auth.response;

import lombok.Data;

@Data
public class LoginResponse  {
    private Boolean succ;
    private String token;
    public LoginResponse(){
        this.succ = false;
        this.token = "";
    }
}

