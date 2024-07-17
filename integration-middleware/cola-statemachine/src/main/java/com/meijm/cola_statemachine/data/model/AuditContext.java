package com.meijm.cola_statemachine.data.model;

import lombok.Data;

@Data
public class AuditContext {

    /**
     * id
     */
    private Long id;

    /**
     * 事件
     */
    private Integer auditEvent;
}