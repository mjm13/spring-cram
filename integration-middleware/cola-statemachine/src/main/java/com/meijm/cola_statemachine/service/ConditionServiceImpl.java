package com.meijm.cola_statemachine.service;

import com.alibaba.cola.statemachine.Condition;
import com.meijm.cola_statemachine.data.model.AuditContext;
import org.springframework.stereotype.Component;

@Component
public class ConditionServiceImpl implements ConditionService {
    @Override
    public Condition<AuditContext> passOrRejectCondition() {
        return context -> {
            System.out.println(1);
            return true;
        };
    }

    @Override
    public Condition<AuditContext> doneCondition() {
        return context -> {
            System.out.println(1);
            return true;
        };
    }
}