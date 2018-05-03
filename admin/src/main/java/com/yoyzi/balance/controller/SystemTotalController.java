package com.yoyzi.balance.controller;

import com.youzi.balance.base.ApiResponse;
import com.yoyzi.balance.controller.request.PayQueryRequest;
import com.yoyzi.balance.controller.request.TotalQueryRequest;
import com.yoyzi.balance.service.PayService;
import com.yoyzi.balance.service.SystemTotalService;
import com.yoyzi.balance.vo.PayVo;
import com.yoyzi.balance.vo.SystemTotalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/total")
@RestController
public class SystemTotalController {

    @Autowired
    private SystemTotalService systemTotalService;

    @RequestMapping("/selectList")
    public ApiResponse<List<SystemTotalVo>> selectList(@RequestBody TotalQueryRequest totalQueryRequest){
        List<SystemTotalVo> systemTotalVos = systemTotalService.selectList(totalQueryRequest);
        return new ApiResponse<>(systemTotalVos);
    }


    @RequestMapping("/selectCount")
    public ApiResponse<Integer> selectCount(@RequestBody TotalQueryRequest payQueryRequest){
        Integer count = systemTotalService.selectCount(payQueryRequest);
        return new ApiResponse<>(count);
    }


}
