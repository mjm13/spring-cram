package com.prolog.rdc.outbound.mq.dao.impl;

import cn.hutool.core.lang.Assert;
import com.prolog.product.rabbitmqreliable.producer.entity.Producer;
import com.prolog.rdc.outbound.mq.dao.ProducerRecordDAO;
import com.prolog.rdc.outbound.mq.dao.mapper.ProducerRecordMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ProducerRecordDAOImpl implements ProducerRecordDAO {

    @Resource
    private ProducerRecordMapper producerRecordMapper;

    /**
     * 查询消费失败记录
     *
     * @param applicationId 应用ID
     * @param messageId 消息ID
     */
    @Override
    public List<Producer> selectProducerByMessageId(String applicationId, List<String> messageId) {
        Assert.notEmpty(applicationId, "应用ID不能为空！");
        Assert.notEmpty(messageId, "消息ID不能为空！");
        return producerRecordMapper.selectProducerByMessageId(applicationId, messageId);
    }
}
