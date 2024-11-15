package com.meijm.basis.controller;

import com.meijm.basis.aop.DynamicAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dynamicAspect")
public class DynamicAspectController {
    @Autowired
    private DynamicAspect dynamicAspect;
    
    @GetMapping("/addNewAspect")
    public void addNewAspect(String pointcut) {
        // 动态添加切点,拦截所有Service类的方法
        dynamicAspect.addPointcut(pointcut,pointcut);
    }

    @GetMapping("/removeAspect")
    public void removeAspect(String pointcut) {
        // 动态移除切点
        dynamicAspect.removePointcut(pointcut);
    }
}
