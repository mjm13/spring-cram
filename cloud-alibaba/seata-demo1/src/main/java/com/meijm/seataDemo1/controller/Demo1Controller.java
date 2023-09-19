package com.meijm.seataDemo1.controller;


import cn.hutool.core.util.StrUtil;
import com.meijm.seataDemo1.service.Demo1Service;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/demo1")
public class Demo1Controller {
    @Autowired
    private Demo1Service demo1Service;
    
    @GetMapping("/updateDemoSleep5")
    public void updateDemoSleep5(String mark) {
        log.info("begin {}", StrUtil.blankToDefault(mark, "updateDemoSleep5"));
        demo1Service.updateDemoSleep5(mark);
        log.info("end  {}", StrUtil.blankToDefault(mark, "updateDemoSleep5"));
    }


    @GetMapping("/updateDemoSleep2")
    public void updateDemoSleep2(String mark) {
        log.info("begin  {}", StrUtil.blankToDefault(mark, "updateDemoSleep2"));
        demo1Service.updateDemoSleep2(mark);
        log.info("end  {}", StrUtil.blankToDefault(mark, "updateDemoSleep2"));
    }


    
    @GetMapping("/updateManyNormalSleep2")
    public void updateManyNormalSleep2(String mark) {
        log.info("begin  {}", StrUtil.blankToDefault(mark, "updateManyNormalSleep2"));
        demo1Service.updateManyNormalSleep2(mark);
        log.info("end  {}", StrUtil.blankToDefault(mark, "updateManyNormalSleep2"));
    }

    
    @GetMapping("/updateManyEndingExceptionSleep5")
    public void updateManyEndingExceptionSleep5(String mark) {
        log.info("begin  {}", StrUtil.blankToDefault(mark, "updateManyEndingExceptionSleep5"));
        demo1Service.updateManyEndingExceptionSleep5(mark);
        log.info("end  {}", StrUtil.blankToDefault(mark, "updateManyEndingExceptionSleep5"));
    }


    
    @GetMapping("/updateDemoError")
    public void updateDemoError() {
        demo1Service.updateDemoError();
    }

    
    @GetMapping("/updateDemoWithOutTransaction")
    public void updateDemoWithOutTransaction() {
        log.info("begin updateDemoWithOutTransaction");
        demo1Service.updateDemoWithOutTransaction();
        log.info("end updateDemoWithOutTransaction");
    }

    
    @GetMapping("/updateManyNormal")
    public void many() {
        demo1Service.updateManyNormal();
    }

    
    @GetMapping("/updateManyEndingException")
    public void updateManyEndingException() {
        demo1Service.updateManyEndingException();
    }

    
    @GetMapping("/updateManyServiceException")
    public void updateManyServiceException() {
        demo1Service.updateManyServiceException();
    }
}
