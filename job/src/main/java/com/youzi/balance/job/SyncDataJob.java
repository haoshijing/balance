package com.youzi.balance.job;

import com.alibaba.druid.pool.DruidDataSource;
import com.youzi.balance.base.mapper.impl.PayMapper;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.mapper.impl.SystemTotalWeekMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemPo;
import com.youzi.balance.base.po.SystemTotalWeekPo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    private SystemTotalWeekMapper systemTotalWeekMapper;


    @EventListener
    public void init(ContextStartedEvent contextStartedEvent) {
        try {
            doWork();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    public void doWork() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0);
        Long end = dateTime.getMillis();
        Long start = dateTime.plusDays(-1).getMillis();
        List<SystemPo> systemPoList = systemMapper.selectAll();
        systemPoList.forEach(systemPo -> {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUsername(systemPo.getDbUserName());
            dataSource.setPassword(systemPo.getDbPassword());
            String url = String.format("jdbc:mysql://%s/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zer",
                    systemPo.getDbHost(), systemPo.getDbName());
            dataSource.setUrl(url);

            String sql = " select id, price , payType ,insertTime , payTime from t_order where orderStatus = 2 and payTime >= " + start
                    + " and payTime <= " + end;


            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<PayPo> payPos = jdbcTemplate.query(sql, new RowMapper<PayPo>() {
                @Override
                public PayPo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PayPo payPo = new PayPo();
                    payPo.setSystemId(systemPo.getId());
                    payPo.setInsertTime(rs.getLong("insertTime"));
                    payPo.setPayTime(rs.getLong("payTime"));
                    payPo.setSyncTime(System.currentTimeMillis());
                    payPo.setMoney(rs.getInt("price"));
                    payPo.setPayType(rs.getString("payType"));
                    payPo.setPayId(rs.getInt("id"));
                    return payPo;
                }
            });

            payPos.forEach(payPo -> {
                payMapper.insert(payPo);
            });
        });
    }

    private void doCalWeek(Integer systemId){
        DateTime dateTime = new DateTime();
        Integer week = dateTime.weekOfWeekyear().get();

        SystemTotalWeekPo queryPo = new SystemTotalWeekPo();
        queryPo.setSystemId(systemId);
        queryPo.setWeek(week);
        Integer count = systemTotalWeekMapper.queryCount(queryPo);

        if(count == 0){
            //
        }
    }

}
