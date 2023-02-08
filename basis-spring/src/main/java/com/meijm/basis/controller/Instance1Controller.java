package com.meijm.basis.controller;

import com.meijm.basis.bean.TestBean;
import com.meijm.basis.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/instance1")
public class Instance1Controller {
    @Autowired
    private TestService testService;

    @GetMapping(value = "/modify")
    public TestBean modify() {
        testService.getTestBean().setTempInt(1);
        testService.getTestBean().setTempStr("tempStr in InstanceTestController.modify");
        return testService.getTestBean();
    }

    @GetMapping(value = "/get")
    public TestBean get() {
        return testService.getTestBean();
    }
}
