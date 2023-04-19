package com.meijm.junit.dao;

import com.google.common.collect.ImmutableList;
import com.meijm.junit.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模拟数据库操作
 */
@Slf4j
@Component
public class DemoDao {
    public long insert(Demo demo){
        log.info("insert方法执行");
        return -1;
    }

    public List<Demo> query(){
        log.info("query方法执行");
        Demo demo = new Demo();
        demo.setI(1);
        demo.setStr("demo");
        demo.setLon(10L);
        return ImmutableList.of(demo);
    }
}
