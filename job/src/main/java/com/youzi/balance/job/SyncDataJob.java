package com.youzi.balance.job;

import com.alibaba.druid.pool.DruidDataSource;
import com.youzi.balance.base.mapper.impl.PayMapper;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.mapper.impl.SystemTotalMonthMapper;
import com.youzi.balance.base.mapper.impl.SystemTotalWeekMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemPo;
import com.youzi.balance.base.po.SystemTotalMonthPo;
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

    @Autowired
    private SystemTotalMonthMapper systemTotalMonthMapper;


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
                try {
                    PayPo queryPo = new PayPo();
                    queryPo.setSystemId(systemPo.getId());
                    queryPo.setPayId(payPo.getPayId());
                    Integer count = payMapper.queryCount(queryPo);
                    if(count == 0) {
                        payMapper.insert(payPo);
                    }
                }catch (Exception e){
                    log.error("",e);
                }
            });
            doCalWeek(systemPo.getId());
            doCalMonth(systemPo.getId());
        });
    }

    private void doCalWeek(Integer systemId){
        DateTime dateTime = new DateTime();
        Integer week = dateTime.weekOfWeekyear().get();
        DateTime start = dateTime.withDayOfWeek(1).withTime(0,0,0,0);
        DateTime end = start.plusWeeks(1);
        SystemTotalWeekPo queryPo = new SystemTotalWeekPo();
        queryPo.setSystemId(systemId);
        queryPo.setWeek(week);
        Integer count = systemTotalWeekMapper.queryCount(queryPo);
        Integer money = payMapper.sumMoney(systemId,start.getMillis(),end.getMillis());

        if(count == 0){
            //
            SystemTotalWeekPo systemTotalWeekPo = new SystemTotalWeekPo();
            systemTotalWeekPo.setWeek(week);
            systemTotalWeekPo.setMoney(money);
            systemTotalWeekPo.setSystemId(systemId);
            systemTotalWeekPo.setYear(String.valueOf(dateTime.getYear()));
            systemTotalWeekMapper.insert(systemTotalWeekPo);

        }else{
            SystemTotalWeekPo updatePo = new SystemTotalWeekPo();
            updatePo.setSystemId(systemId);
            updatePo.setWeek(week);
            updatePo.setMoney(money);
            systemTotalWeekMapper.update(updatePo);
        }
    }

    private void doCalMonth(Integer systemId){
        DateTime dateTime = new DateTime();
        Integer month = dateTime.monthOfYear().get();
        DateTime start = dateTime.withDayOfMonth(1).withTime(0,0,0,0);
        DateTime end = start.plusMonths(1);
        SystemTotalMonthPo queryPo = new SystemTotalMonthPo();
        queryPo.setSystemId(systemId);
        queryPo.setMonth(month);
        Integer count = systemTotalMonthMapper.queryCount(queryPo);
        Integer money = payMapper.sumMoney(systemId,start.getMillis(),end.getMillis());

        if(count == 0){
            //
            SystemTotalMonthPo insertPo = new SystemTotalMonthPo();
            insertPo.setMonth(month);
            insertPo.setMoney(money);
            insertPo.setSystemId(systemId);
            insertPo.setYear(String.valueOf(dateTime.getYear()));
            systemTotalMonthMapper.insert(insertPo);

        }else{
            SystemTotalMonthPo updatePo = new SystemTotalMonthPo();
            updatePo.setSystemId(systemId);
            updatePo.setMonth(month);
            updatePo.setMoney(money);
            systemTotalMonthMapper.update(updatePo);
        }
    }

    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        System.out.println("args = [" + dateTime.withDayOfWeek(1).withTime(0,0,0,0).getMillis() + "]");

    }

}
