package com.prolog.rdc.outbound.mq.dao;

import com.prolog.product.rabbitmqreliable.producer.entity.Producer;

import java.util.List;

/**
 * 数据访问层
 */
public interface ProducerRecordDAO {

    /**
     * 查询消费失败记录
     */
    List<Producer> selectProducerByMessageId(String applicationId, List<String> messageId);


}
