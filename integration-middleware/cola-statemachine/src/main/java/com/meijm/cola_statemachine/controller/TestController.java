package com.meijm.cola_statemachine.controller;

import com.meijm.cola_statemachine.data.dto.AuditParam;
import com.meijm.cola_statemachine.data.model.AuditContext;
import com.meijm.cola_statemachine.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/audit")
    public void audit(@RequestBody @Validated AuditParam auditParam){
        AuditContext auditContext = new AuditContext();
        BeanUtils.copyProperties(auditParam, auditContext);
        auditService.audit(auditContext);
    }

    @GetMapping("/uml")
    public String uml(){
        return auditService.uml();
    }
}