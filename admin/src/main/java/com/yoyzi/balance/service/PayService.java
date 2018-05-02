package com.yoyzi.balance.service;

import com.google.common.collect.Maps;
import com.youzi.balance.base.mapper.impl.PayMapper;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemPo;
import com.yoyzi.balance.controller.request.PayQueryRequest;
import com.yoyzi.balance.vo.PayVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PayService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private SystemMapper systemMapper;

    public List<PayVo> selectList(PayQueryRequest payQueryRequest){

        Integer limit = payQueryRequest.getLimit();
        Integer page = payQueryRequest.getPage();

        Integer offset = (page - 1)* limit;
        Integer systemId = payQueryRequest.getSystemId();

        PayPo queryPo = new PayPo();
        queryPo.setSystemId(systemId);

        Map<Integer,String> systemMap = Maps.newHashMap();

        List<PayVo> payVos = payMapper.selectList(queryPo,limit,offset).stream().map(
                payPo -> {
                    PayVo payVo = new PayVo();
                    payVo.setPayType(payPo.getPayType());
                    if(!systemMap.containsKey(payPo.getSystemId())){
                        SystemPo systemPo = systemMapper.findById(payPo.getSystemId());
                        if(systemPo != null){
                            systemMap.put(payPo.getSystemId(),systemPo.getName());
                        }
                    }
                    payVo.setId(payPo.getId());
                    payVo.setSystemName(systemMap.get(payPo.getSystemId()));
                    payVo.setMoney(payPo.getMoney() /100+"元");
                    payVo.setPayTime(new DateTime(payPo.getPayTime()).toString("yyyy-MM-dd HH:mm:ss"));
                    return payVo;
                }
        ).collect(Collectors.toList());
        return  payVos;

    }

    public Integer selectCount(PayQueryRequest request){
        PayPo queryPo = new PayPo();
        Integer systemId = request.getSystemId();

        queryPo.setSystemId(systemId);

        return payMapper.selectCount(queryPo);

    }
}
