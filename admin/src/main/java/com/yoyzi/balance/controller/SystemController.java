package com.yoyzi.balance.controller;


import com.youzi.balance.base.ApiResponse;
import com.yoyzi.balance.controller.request.PayQueryRequest;
import com.yoyzi.balance.controller.request.SystemUpdatePo;
import com.yoyzi.balance.service.PayService;
import com.yoyzi.balance.service.SystemService;
import com.yoyzi.balance.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;
    @RequestMapping("/addOrUpdate")
    public ApiResponse<Boolean> addOrUpdate(@RequestBody SystemUpdatePo systemUpdatePo){
        boolean ret = systemService.addOrUpdateSystem(systemUpdatePo);
        return new ApiResponse<>(ret);
    }
}
