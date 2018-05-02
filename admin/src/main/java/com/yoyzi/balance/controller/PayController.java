package com.yoyzi.balance.controller;

import com.youzi.balance.base.ApiResponse;
import com.yoyzi.balance.controller.request.PayQueryRequest;
import com.yoyzi.balance.service.PayService;
import com.yoyzi.balance.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping("/selectList")
    public ApiResponse<List<PayVo>> selectList(@RequestBody PayQueryRequest request){
        List<PayVo> payVos = payService.selectList(request);
        return new ApiResponse(payVos);
    }

    @RequestMapping("/selectCount")
    public ApiResponse<Integer> selectCount(@RequestBody PayQueryRequest payQueryRequest){
        Integer count = payService.selectCount(payQueryRequest);
        return new ApiResponse<>(count);
    }
}
