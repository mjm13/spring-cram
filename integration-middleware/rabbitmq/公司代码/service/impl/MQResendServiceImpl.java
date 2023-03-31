package com.prolog.rdc.outbound.mq.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.prolog.framework.core.pojo.Page;
import com.prolog.product.core.base.pojo.RequestPage;
import com.prolog.product.rabbitmqreliable.producer.entity.Producer;
import com.prolog.rdc.outbound.mq.component.RabbitMQResendProducer;
import com.prolog.rdc.outbound.mq.dao.ConsumerFailRecordDAO;
import com.prolog.rdc.outbound.mq.dao.ProducerRecordDAO;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecord;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecordQueryBO;
import com.prolog.rdc.outbound.mq.service.MQResendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MQResendServiceImpl implements MQResendService {

    @Resource
    private ProducerRecordDAO producerRecordDAO;

    @Resource
    private ConsumerFailRecordDAO consumerFailRecordDAO;

    @Resource
    private RabbitMQResendProducer rabbitMQResendProducer;

    @Value("${spring.application.name:}")
    private String applicationId;

    /**
     * 保存消费失败记录
     *
     * @param consumerFailRecord 失败记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveRecord(ConsumerFailRecord consumerFailRecord) {
        consumerFailRecordDAO.saveRecord(consumerFailRecord);
    }

    /**
     * 查询消费失败记录
     *
     * @param bo 查询条件
     */
    @Override
    public Page<ConsumerFailRecord> pageRecord(RequestPage<ConsumerFailRecordQueryBO> bo) {
        return consumerFailRecordDAO.pageRecord(bo);
    }

    /**
     * 通过messageId批量删除
     *
     * @param ids id
     */
    @Override
    public void deleteRecordByMessageIds(List<String> ids) {
        Assert.notEmpty(ids, "消息ID不能为空");
        consumerFailRecordDAO.deleteRecordByMessageIds(applicationId, ids);
    }

    /**
     * 重发消息
     *
     * @param ids 失败记录ID
     */
    @Override
    public void resendMessage(List<String> ids) {
        Assert.notEmpty(ids, "请选择要重发的数据！");
        List<ConsumerFailRecord> list = consumerFailRecordDAO.selectMessageIdByIds(ids);
        List<String> messageIds = list.stream().map(ConsumerFailRecord::getMessageId).collect(Collectors.toList());
        List<Producer> producerList = producerRecordDAO.selectProducerByMessageId(applicationId, messageIds);
        List<String> updateMessageIds = rabbitMQResendProducer.resendMessage(producerList);
        log.info("已重发消息：{}", JSONUtil.toJsonStr(updateMessageIds)   );
    }
}
