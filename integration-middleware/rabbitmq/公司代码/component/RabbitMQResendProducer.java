package com.prolog.rdc.outbound.mq.component;

import cn.hutool.json.JSONUtil;
import com.prolog.product.rabbitmqreliable.common.job.AbstractWorker;
import com.prolog.product.rabbitmqreliable.producer.entity.Producer;
import com.prolog.product.rabbitmqreliable.producer.manager.ProducerManager;
import com.prolog.product.rabbitmqreliable.producer.manager.RabbitTemplateManager;
import com.prolog.product.rabbitmqreliable.producer.util.ProducerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * RabbitMq重发任务
 */
@Component
public class RabbitMQResendProducer extends AbstractWorker {
    private final Logger logger = LoggerFactory.getLogger(RabbitMQResendProducer.class);

    public final static String RELIABLE_RABBIT_TEMPLATE = "reliable_JsonRabbitTemplate";

    @Value("${spring.application.name}")
    private String application;
    @Resource
    private ProducerManager producerManager;
    @Resource
    private RabbitTemplateManager rabbitTemplateManager;
//    @Resource
//    private RabbitSequenceMessagePostProcessor rabbitSequenceMessagePostProcessor;

    public List<String> resendMessage(List<Producer> list) {
        List<String> messageIds = new ArrayList<>();
        for (Producer producer : list) {
            try {
                producerManager.failSendMq(application, producer.getMessageId());
                RabbitTemplate rabbitTemplate = rabbitTemplateManager.getRabbitTemplate(producer.getHost(),
                        producer.getPort(), producer.getVirtualHost(), RELIABLE_RABBIT_TEMPLATE);
                if (Objects.isNull(rabbitTemplate)) {
                    logger.error("找不到RabbitTemplate，host:{}, port:{}, virtualHost:{}", producer.getHost(),
                            producer.getPort(), producer.getVirtualHost());
                    continue;
                }
                if (Objects.isNull(producer.getMessage())) {
                    logger.error("消息的消息体为空：{}", JSONUtil.toJsonStr(producer));
                    continue;
                }
                Message message = recoverMessage(producer.getMessage());
                CorrelationData correlationData =
                        ProducerUtils.generateCorrelationData(producer.getApplication(), producer.getMessageId());
                rabbitTemplate.send(producer.getExchange(), producer.getRoutingKey(), message, correlationData);
                messageIds.add(producer.getMessageId());
            } catch (Throwable ignore) {
                logger.error("message：{}重发失败！", producer.getMessageId(), ignore);
            }
        }
        return messageIds;
    }


    @Override
    public void start() {
//        int batchSize = producerResendProperties.getBatchSize();
//        int size = resendMessageAuto(batchSize);
//        if (size < batchSize) {
//            try {
//                Thread.sleep(producerResendProperties.getWorkPeriod());
//            } catch (InterruptedException ignore) {
//            }
//        }
    }

    /*public int resendMessageAuto(int batchSize) {
        List<Producer> list = producerManager.listSendingMq(producerResendProperties.getApplication(), 0, batchSize);
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        resendMessage(list);
        return list.size();
    }*/

    private Message recoverMessage(byte[] messageBytes) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(messageBytes));
        Message message = (Message)oin.readObject();
        return message;
    }

}
