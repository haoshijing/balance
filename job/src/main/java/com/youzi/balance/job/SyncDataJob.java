package com.youzi.balance.job;

import com.alibaba.druid.pool.DruidDataSource;
import com.youzi.balance.base.mapper.impl.PayMapper;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.mapper.impl.SystemTotalMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemPo;
import com.youzi.balance.base.po.SystemTotalPo;
import com.youzi.balance.job.service.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:23
 **/
@Repository
@Slf4j
public class SyncDataJob {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private SystemTotalMapper systemTotalMapper;

    @Autowired
    private SyncService syncService;


    @Scheduled(cron = "0 */20 * * * ?")
    public void sync() {
        try {
            log.info("开始同步");
            doWork();
            log.info("同步结束");
        } catch (Exception e) {
            log.error("", e);
        }
    }


    public void doWork() {
        List<SystemPo> systemPoList = systemMapper.selectAll();
        systemPoList.forEach(systemPo -> {
            syncService.syncData(systemPo,0);
            doCalMonth(systemPo.getId());
            DateTime dateTime = new DateTime();
            Integer week = dateTime.weekOfWeekyear().get();
            doCalWeek(systemPo.getId(),week);
        });
    }

    public void doCalWeek(Integer systemId,int week){
        DateTime dateTime = new DateTime();
        DateTime start = dateTime.withWeekOfWeekyear(week).withTime(0,0,0,0);
        DateTime end = start.plusWeeks(1);
        doCal(start,end,systemId,1,week);
    }

    public void doCalMonth(Integer systemId){
        DateTime dateTime = new DateTime();
        Integer month = dateTime.monthOfYear().get();
        DateTime start = dateTime.withDayOfMonth(1).withTime(0,0,0,0);
        DateTime end = start.plusMonths(1);
        doCal(start,end,systemId,2,month);

    }

    private void doCal(DateTime start,DateTime end,Integer systemId,Integer type,Integer index){
        SystemTotalPo queryPo = new SystemTotalPo();
        queryPo.setSystemId(systemId);
        queryPo.setIndexAt(index);
        queryPo.setTypeVal(type);
        Integer count = systemTotalMapper.queryCount(queryPo);
        Integer money = payMapper.sumMoney(systemId,start.getMillis(),end.getMillis());

        if(count == 0){
            //
            SystemTotalPo systemPo = new SystemTotalPo();
            systemPo.setIndexAt(index);
            systemPo.setMoney(money);
            systemPo.setTypeVal(type);
            systemPo.setSystemId(systemId);
            systemPo.setYearStr(String.valueOf(start.getYear()));
            systemTotalMapper.insert(systemPo);

        }else{
            SystemTotalPo updatePo = new SystemTotalPo();
            updatePo.setSystemId(systemId);
            updatePo.setIndexAt(index);
            updatePo.setTypeVal(type);
            updatePo.setMoney(money);
            systemTotalMapper.update(updatePo);
        }
    }

    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        System.out.println("args = [" + dateTime.withDayOfWeek(1).withTime(0,0,0,0).getMillis() + "]");

    }

}
