package com.prolog.rdc.outbound.mq.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 消费失败记录
 */
@Data
@Builder
public class ConsumerFailRecordBO {

    private Long id;

    /**
     * 应用ID
     */
    private String application;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 业务编号
     */
    private String businessNo;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 消费失败原因
     */
    private String failMessage;

    /**
     * 消费失败时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date lastModifiedDate;
}
