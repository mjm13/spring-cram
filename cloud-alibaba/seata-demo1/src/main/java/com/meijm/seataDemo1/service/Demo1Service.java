package com.meijm.seataDemo1.service;

import cn.hutool.core.util.StrUtil;
import com.meijm.seataDemo1.dao.Demo1Dao;
import com.meijm.seataDemo1.entity.Demo1;
import com.meijm.seataDemo1.feign.Demo2Feign;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Demo1Service {
    @Autowired
    private Demo1Dao demo1Dao;
    @Autowired
    private Demo2Feign demo2Feign;

    @GlobalTransactional(rollbackFor = Exception.class, timeoutMills = 600000)
    public void updateDemoSleep5(String mark) {
        if (StrUtil.isBlank(mark)) {
            mark = "demo1-updateDemoSleep5";
        }
        log.info("begin service {}", mark);
        try {
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark(mark);
            demo1Dao.save(demo1);
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("end service {}", mark);
    }

    @GlobalTransactional(rollbackFor = Exception.class, timeoutMills = 600000)
    public void updateDemoSleep2(String mark) {
        if (StrUtil.isBlank(mark)) {
            mark = "demo1-updateDemoSleep2";
        }
        log.info("begin service {}", mark);
        try {
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark(mark);
            demo1Dao.save(demo1);
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("end service {}", mark);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateDemoError() {
        Demo1 demo1 = new Demo1();
        demo1.setId(1L);
        demo1.setMark("demo1-updateDemoError");
        demo1Dao.save(demo1);
        log.info("demo1-updateDemoError");
        throw new RuntimeException("xxxx");
    }

    public void updateDemoWithOutTransaction() {
        log.info("begin service demo1-updateDemoWithOutTransaction");
        Demo1 demo1 = new Demo1();
        demo1.setId(1L);
        demo1.setMark("demo1-updateDemoWithOutTransaction");
        demo1Dao.save(demo1);
        log.info("begin service demo1-updateDemoWithOutTransaction");
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateManyNormal() {
        log.info("begin service demo1-updateManyNormal");
        Demo1 demo1 = new Demo1();
        demo1.setId(1L);
        demo1.setMark("demo1-updateManyNormal");
        demo1Dao.save(demo1);
        Boolean temp = demo2Feign.updateDemo2();
        if (!temp) {
            throw new RuntimeException("下游服务异常");
        }
        log.info("end service demo1-updateManyNormal");
    }


    @GlobalLock
    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateManyNormalSleep2(String mark) {
        if (StrUtil.isBlank(mark)) {
            mark = "demo1-updateManyNormalSleep2";
        }
        log.info("begin service demo1-updateManyNormalSleep2");
        try {
            Demo1 demo1Db = demo1Dao.findById(1L).orElse(null);
            log.info("updateManyNormalSleep2--demo1---before submit mark:{}", demo1Db.getMark());
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark(mark);
            Thread.sleep(2000L);
            demo1Dao.save(demo1);
            Boolean temp = demo2Feign.updateDemo2();
            if (!temp) {
                throw new RuntimeException("下游服务异常");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("end service demo1-updateManyNormalSleep2");
    }

    @GlobalLock
    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateManyEndingExceptionSleep5(String mark) {
        if (StrUtil.isBlank(mark)) {
            mark = "demo1-updateManyEndingException";
        }
        log.info("begin service {}", mark);
        try {
            Demo1 demo1Db = demo1Dao.findById(1L).orElse(null);
            log.info("updateManyEndingExceptionSleep5--demo1---before submit mark:{}", demo1Db.getMark());
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark(mark);
            demo1Dao.save(demo1);
            Thread.sleep(5000L);
            Boolean temp = demo2Feign.updateDemo2();
            if (!temp) {
                throw new RuntimeException("下游服务异常");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        log.info("end service {}", mark);
        throw new RuntimeException("服务调用完成后异常");
    }


    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateManyEndingException() {
        log.info("begin service demo1-updateManyEndingException");
        try {
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark("demo1-updateManyEndingException");
            Thread.sleep(1000L);
            demo1Dao.save(demo1);
            Boolean temp = demo2Feign.updateDemo2();
            if (!temp) {
                throw new RuntimeException("下游服务异常");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        log.info("end service demo1-updateManyEndingException");
        throw new RuntimeException("服务调用完成后异常");
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateManyServiceException() {
        log.info("begin service demo1-updateManyServiceException");
        try {
            Demo1 demo1 = new Demo1();
            demo1.setId(1L);
            demo1.setMark("updateManyServiceException");
            demo1Dao.save(demo1);
            Thread.sleep(1000L);
            Boolean temp = demo2Feign.updateDemo2ThrowException();
            if (!temp) {
                throw new RuntimeException("下游服务异常");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        log.info("begin service demo1-updateManyServiceException");
    }
}
