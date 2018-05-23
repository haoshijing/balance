package com.yoyzi.balance.controller;

import com.youzi.balance.base.ApiResponse;
import com.yoyzi.balance.controller.request.PayQueryRequest;
import com.yoyzi.balance.service.PayService;
import com.yoyzi.balance.vo.PayVo;
import com.yoyzi.balance.vo.TotalList;
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
    public ApiResponse<TotalList> selectList(@RequestBody PayQueryRequest request){
        List<PayVo> payVos = payService.selectList(request);
        Integer totalAmount = payService.selectAmount(request);
        TotalList totalList = new TotalList();
        totalList.setPayVoList(payVos);
        if(totalAmount == null){
            totalAmount = 0;
        }
        totalList.setTotalAmount(totalAmount);
        return new ApiResponse(totalList);
    }

    @RequestMapping("/selectCount")
    public ApiResponse<Integer> selectCount(@RequestBody PayQueryRequest payQueryRequest){
        Integer count = payService.selectCount(payQueryRequest);
        return new ApiResponse<>(count);
    }
}
