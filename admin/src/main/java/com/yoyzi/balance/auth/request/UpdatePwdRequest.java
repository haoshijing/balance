package com.yoyzi.balance.auth.request;

import lombok.Data;

@Data
public class UpdatePwdRequest {
    private String oldPwd;
    private String newPwd;
}
