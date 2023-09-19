package com.meijm.seataDemo2.service;

import com.meijm.seataDemo2.entity.Demo2;
import com.meijm.seataDemo2.dao.Demo2Dao;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Demo2Service {
    @Autowired
    private Demo2Dao demo2Dao;


    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateDemo2(){
        log.info("DEMO 全局事务id :{}", RootContext.getXID());
        Demo2 demo2 = new Demo2();
        demo2.setId(1L);
        demo2.setMark("demo2-updateDemo2");
        demo2Dao.save(demo2);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateDemo2ThrowException(){
        log.info("DEMO 全局事务id :{}", RootContext.getXID());
        Demo2 demo2 = new Demo2();
        demo2.setId(1L);
        demo2.setMark("demo2-updateDemo2ThrowException");
        demo2Dao.save(demo2);
        throw new RuntimeException("服务demo2 更新异常");
    }
}
