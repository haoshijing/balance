package com.yoyzi.balance.service;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.youzi.balance.base.mapper.impl.SystemMapper;
import com.youzi.balance.base.po.SystemPo;
import com.yoyzi.balance.controller.request.SystemUpdatePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemService {

    @Autowired
    SystemMapper systemMapper;
    public boolean addOrUpdateSystem(SystemUpdatePo systemUpdatePo){

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(systemUpdatePo.getDbUserName());
        dataSource.setPassword(systemUpdatePo.getDbPassword());
        String url = String.format("jdbc:mysql://%s/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zer",
                systemUpdatePo.getHost(), systemUpdatePo.getDbName());
        dataSource.setUrl(url);
        boolean isConnect = true;
        try {
            dataSource.getConnection();
        }catch (Exception e){
            isConnect =false;
        }
        if(!isConnect){
            return false;
        }
        int ret = -1;
        if(systemUpdatePo.getId() > 0) {
            SystemPo updatePo = new SystemPo();
            updatePo.setDbHost(systemUpdatePo.getHost());
            updatePo.setDbName(systemUpdatePo.getDbName());
            updatePo.setDbPassword(systemUpdatePo.getDbPassword());
            updatePo.setDbUserName(systemUpdatePo.getDbUserName());
            updatePo.setId(systemUpdatePo.getId());
            ret =  systemMapper.update(updatePo);
        }else{
            SystemPo insertPo = new SystemPo();
            insertPo.setDbHost(systemUpdatePo.getHost());
            insertPo.setDbName(systemUpdatePo.getDbName());
            insertPo.setDbPassword(systemUpdatePo.getDbPassword());
            insertPo.setDbUserName(systemUpdatePo.getDbUserName());
            insertPo.setName(systemUpdatePo.getName());
            insertPo.setId(systemUpdatePo.getId());
            ret =  systemMapper.insert(insertPo);
        }
        return ret >0;

    }
}
