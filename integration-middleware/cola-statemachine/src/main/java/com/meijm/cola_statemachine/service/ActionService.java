package com.meijm.cola_statemachine.service;

import com.alibaba.cola.statemachine.Action;
import com.meijm.cola_statemachine.data.enums.AuditEvent;
import com.meijm.cola_statemachine.data.enums.AuditState;
import com.meijm.cola_statemachine.data.model.AuditContext;

public interface ActionService {

    /**
     * 通用审核通过/驳回执行动作
     * 覆盖审核正向流程，以及驳回流程
     * 已申请->爸爸同意->妈妈同意
     * 已申请->爸爸不同意
     * 爸爸同意->妈妈不同意
     *
     * @return Action
     */
    Action<AuditState, AuditEvent, AuditContext> passOrRejectAction();

    /**
     * 已完成执行动作
     *
     * @return Action
     */
    Action<AuditState, AuditEvent, AuditContext> doneAction();
}