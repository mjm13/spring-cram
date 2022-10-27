package com.meijm.dynamicdb.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.meijm.dynamicdb.dao.TestDao;
import com.meijm.dynamicdb.entity.Test;
import com.meijm.dynamicdb.vo.DataSourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DemoService {
    //动态数据源
    @Autowired
    private DynamicRoutingDataSource drds;
    //数据源创建器
    @Autowired
    private BasicDataSourceCreator dataSourceCreator;
    @Autowired
    private TestDao testDao;

    public List<Test> changeSourceAndQuery(String poolName){
        List<Test> result = new ArrayList<>();
        DynamicDataSourceContextHolder.push(poolName);//数据源名称
        try {
            result =  testDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
        return result;
    }

    //创建数据源
    public void createDataSource(DataSourceVo vo) {
        DataSourceProperty dsp = new DataSourceProperty();
        dsp.setPoolName(vo.getPoolName());//链接池名称
        dsp.setUrl(vo.getUrl());//数据库连接
        dsp.setUsername(vo.getUsername());//用户名
        dsp.setPassword(vo.getPassword());//密码
        dsp.setDriverClassName(vo.getDriverClassName());//驱动
        //创建数据源并添加到系统中管理
        DataSource dataSource = dataSourceCreator.createDataSource(dsp);
        drds.addDataSource(vo.getPoolName(), dataSource);
    }

    public void removeDataSource(String poolName) {
//        DynamicRoutingDataSource drds = (DynamicRoutingDataSource) dataSource;
        drds.removeDataSource(poolName);
    }

    public Set<String> getDataSourceKeys() {
//        DynamicRoutingDataSource drds = (DynamicRoutingDataSource) dataSource;
        return drds.getDataSources().keySet();
    }

}
