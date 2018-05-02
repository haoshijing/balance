package com.yoyzi.balance.controller;


import com.yoyzi.balance.auth.AdminAuthInfo;
import com.yoyzi.balance.service.auth.AdminAuthCacheService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class AbstractController {
    @Autowired
    protected AdminAuthCacheService adminAuthCacheService;
    private static  final String TOKEN = "X-TOKEN";
    public AdminAuthInfo getToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(TOKEN);
        return adminAuthCacheService.getByToken(token);
    }
}
