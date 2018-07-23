package com.youzi.balance.job.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.youzi.balance.base.mapper.impl.PayMapper;
import com.youzi.balance.base.po.PayPo;
import com.youzi.balance.base.po.SystemPo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author haoshijing
 * @version 2018年05月03日 11:30
 **/

@Service
@Slf4j
public class SyncService {

    @Autowired
    private PayMapper payMapper;

    public void syncData(SystemPo systemPo, int day) {
        try{
            doSyncData(systemPo,day);
        }catch (Exception e){
            log.error("",e);
        }
    }

    private void doSyncData(SystemPo systemPo, int day){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(day).withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0);
        Long end = dateTime.getMillis();
        Long start = dateTime.plusDays(-1).getMillis();

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(systemPo.getDbUserName());
        dataSource.setPassword(systemPo.getDbPassword());
        dataSource.setMaxWait(2000);
        String url = String.format("jdbc:mysql://%s/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zer",
                systemPo.getDbHost(), systemPo.getDbName());
        dataSource.setUrl(url);

        String sql = " select id, price , payType ,insertTime , payTime from t_order where orderStatus in(2,3) and insertTime >= " + start
                + " and insertTime <= " + end;

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PayPo> payPos = jdbcTemplate.query(sql, new RowMapper<PayPo>() {
            @Override
            public PayPo mapRow(ResultSet rs, int rowNum) throws SQLException {
                PayPo payPo = new PayPo();
                payPo.setSystemId(systemPo.getId());
                payPo.setInsertTime(rs.getLong("insertTime"));
                payPo.setPayTime(rs.getLong("insertTime"));
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
                if (count == 0) {
                    payMapper.insert(payPo);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        });
        dataSource.close();
    }
}
