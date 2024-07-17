package com.meijm.cola_statemachine.data.dto;

import lombok.Data;

@Data
public class AuditParam {

    /**
     * id
     */
    private Long id;

    /**
     * 事件
     */
    private Integer auditEvent;
}