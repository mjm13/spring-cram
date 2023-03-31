package com.prolog.rdc.outbound.mq.entity;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * 消费失败记录
 */
@Data
@Table("consumer_fail_record")
public class ConsumerFailRecord {

    @Id
    private Long id;

    /**
     * 应用ID
     */
    @Column("application")
    private String application;

    /**
     * 消息ID
     */
    @Column("message_id")
    private String messageId;

    /**
     * 业务编号
     */
    @Column("business_no")
    private String businessNo;

    /**
     * 业务类型
     */
    @Column("business_type")
    private Integer businessType;

    /**
     * 消费失败原因
     */
    @Column("fail_message")
    private String failMessage;

    /**
     * 消费失败时间
     */
    @Column("created_date")
    private Date createdDate;

    /**
     * 更新时间
     */
    @Column("last_modified_date")
    private Date lastModifiedDate;
}
