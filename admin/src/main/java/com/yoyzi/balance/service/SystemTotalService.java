package com.yoyzi.balance.service;

import com.google.common.collect.Maps;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.mapper.impl.SystemTotalMapper;
import com.youzi.balance.base.po.SystemPo;
import com.youzi.balance.base.po.SystemTotalPo;
import com.yoyzi.balance.controller.request.TotalQueryRequest;
import com.yoyzi.balance.vo.SystemTotalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SystemTotalService {

    @Autowired
    private SystemTotalMapper systemTotalMapper;

    @Autowired
    private SystemMapper systemMapper;

    public List<SystemTotalVo> selectList(TotalQueryRequest request){
        SystemTotalPo systemTotalPo = new SystemTotalPo();

        systemTotalPo.setTypeVal(request.getType());
        systemTotalPo.setSystemId(request.getSystemId());
        systemTotalPo.setYearStr(request.getYear());
        systemTotalPo.setIndexAt(request.getIndex());

        Integer limit = request.getLimit();
        Integer offset = (request.getPage() - 1)*limit;

        List<SystemTotalPo> systemTotalPos = systemTotalMapper.selectList(systemTotalPo,limit,offset);
        Map<Integer,String> systemMap = Maps.newHashMap();

        return systemTotalPos.stream().map(systemTotalPo1 -> {
            SystemTotalVo systemTotalVo = new SystemTotalVo();
            systemTotalVo.setId(systemTotalPo1.getId());
            systemTotalVo.setIndex(systemTotalPo1.getIndexAt());
            if(!systemMap.containsKey(systemTotalPo1.getSystemId())){
                SystemPo systemPo = systemMapper.findById(systemTotalPo1.getSystemId());
                if(systemPo != null){
                    systemMap.put(systemTotalPo1.getSystemId(),systemPo.getName());
                }
            };
            systemTotalVo.setSystemName(systemMap.get(systemTotalPo1.getSystemId()));
            systemTotalVo.setYear(systemTotalPo1.getYearStr());
            if(systemTotalPo1.getMoney() != null) {
                systemTotalVo.setMoney(systemTotalPo1.getMoney() / 100 + "元");
            }else{
                systemTotalVo.setMoney( "0元");
            }
            return systemTotalVo;
        }).collect(Collectors.toList());
    }

    public Integer selectCount(TotalQueryRequest request){
        SystemTotalPo systemTotalPo = new SystemTotalPo();
        systemTotalPo.setTypeVal(request.getType());
        systemTotalPo.setIndexAt(request.getIndex());
        systemTotalPo.setSystemId(request.getSystemId());
        return systemTotalMapper.selectCount(systemTotalPo);
    }


}
