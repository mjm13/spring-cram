package com.meijm.junit.service;

import com.google.common.collect.ImmutableList;
import com.meijm.junit.dao.DemoDao;
import com.meijm.junit.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DemoService {
    @Autowired
    private DemoDao demoDao;

    public long insert(Demo demo){
        log.info("insert方法执行");
        return demoDao.insert(demo);
    }

    public List<Demo> query(){
        log.info("query方法执行");
        return demoDao.query();
    }
}
