package com.youzi.balance.job;

import com.alibaba.druid.pool.DruidDataSource;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.po.SystemPo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author haoshijing
 * @version 2018年04月27日 13:23
 **/
@Repository
@Slf4j
public class SyncDataJob {

    @Autowired
    private  SystemMapper systemMapper;

    public void doWork(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0);
        Long end = dateTime.getMillis();
        Long start = dateTime.plusDays(-1).getMillis();
        List<SystemPo> systemPoList = systemMapper.selectAll();
        systemPoList.forEach(systemPo -> {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUsername(systemPo.getDbUserName());
            dataSource.setPassword(systemPo.getDbPassword());
            dataSource.setUsername("jdbc");
        });
    }
}
