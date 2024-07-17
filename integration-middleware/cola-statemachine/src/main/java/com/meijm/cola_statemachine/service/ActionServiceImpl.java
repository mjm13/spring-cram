package com.meijm.cola_statemachine.service;

import com.alibaba.cola.statemachine.Action;
import com.meijm.cola_statemachine.data.enums.AuditEvent;
import com.meijm.cola_statemachine.data.enums.AuditState;
import com.meijm.cola_statemachine.data.model.AuditContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionServiceImpl implements ActionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionServiceImpl.class);

//    @Autowired
//    private AuditDao auditDao;

    @Override
    public Action<AuditState, AuditEvent, AuditContext> passOrRejectAction() {
        return (from, to, event, context) -> {
            LOGGER.info("passOrRejectAction from {}, to {}, on event {}, id:{}", from, to, event, context.getId());
//            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }

    @Override
    public Action<AuditState, AuditEvent, AuditContext> doneAction() {
        return (from, to, event, context) -> {
            LOGGER.info("doneAction from {}, to {}, on event {}, id:{}", from, to, event, context.getId());
//            auditDao.updateAuditStatus(to.getCode(), context.getId());
        };
    }
}