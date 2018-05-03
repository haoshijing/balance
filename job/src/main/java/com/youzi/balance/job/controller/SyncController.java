package com.youzi.balance.job.controller;

import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.po.SystemPo;
import com.youzi.balance.job.service.SyncService;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author haoshijing
 * @version 2018年05月03日 11:20
 **/

@RequestMapping("/history")
@Controller
@Slf4j
public class SyncController {

    private DefaultEventExecutorGroup defaultEventExecutorGroup = new DefaultEventExecutorGroup(8, new DefaultThreadFactory("SyncDataThread"));

    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private SyncService syncService;

    @RequestMapping("/index")
    public String syncHistory(Integer systemId, Integer days) {
        SystemPo systemPo = systemMapper.findById(systemId);

        List<Future> futureList = Lists.newArrayList();
        if (systemPo != null) {
            for (int idx = 0; idx < days; idx++) {
                final int day = idx;
                Future f = defaultEventExecutorGroup.submit(new Runnable() {
                    @Override
                    public void run() {
                        syncService.syncData(systemPo, 0 - day);
                    }
                }).addListener(future -> {
                    log.info("同步完成");
                });
                futureList.add(f);
            }
        }

        for(Future f: futureList){
            try {
                f.get();
            }catch (Exception e){

            }
        }
        return "ok";
    }
}
