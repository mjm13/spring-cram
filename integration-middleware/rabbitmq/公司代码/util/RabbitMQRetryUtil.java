package com.prolog.rdc.outbound.mq.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import com.prolog.product.rabbitmqreliable.common.util.RabbitUtils;
import com.prolog.product.rabbitmqreliable.comsumer.manager.ConsumerRecordManager;
import com.prolog.rdc.outbound.common.util.RedisUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.function.Consumer;

@Component
@Slf4j
public class RabbitMQRetryUtil {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ConsumerRecordManager consumerRecordManager;
    @Value("${spring.application.name}")
    private String applicationName;

    private final String rabbitRetryPreKey = "WMS::RABBITMQ::RETRY::";

    private final long defaultInitialInterval = 1000;//默认重试间隔时间
    private final int defaultMultiplier = 2;//默认重试间隔时间乘积
    private final long defaultMaxInterval = 5000;//默认最大重试间隔时间
    private final int defaultMaxAttempts = 3;//默认最大重试次数

    /**
     * 通过Redis将消息ID作为key，存储该消息已重试次数
     * @param message 消息
     * @param initialInterval 重试间隔时间 单位毫秒 1000ms
     * @param multiplier 重试间隔时间乘积 2: 10000ms, 2000ms, 4000ms
     * @param maxInterval 最大间隔时间 单位毫秒 3000ms
     * @param maxAttempts 最大重试次数 3
     */
    public void retry(Message message, Channel channel, long initialInterval, int multiplier, long maxInterval, int maxAttempts,
                      Runnable runnable, Consumer<String> errorCallback) {
        String messageId = message.getMessageProperties().getMessageId();
        try {
            Assert.notEmpty(messageId, "消息ID不能为空！");
            Assert.isTrue(initialInterval > 0, "重试间隔时间必须大于0！");
            Assert.isTrue(multiplier > 0, "重试间隔时间乘积必须大于0！");
            Assert.isTrue(maxInterval > 0, "最大间隔时间必须大于0！");
            Assert.isTrue(maxAttempts > 0, "最大重试次数必须大于0！");
            runnable.run();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            Integer retryCount = Convert.toInt(redisUtil.get(rabbitRetryPreKey + messageId), 0);
            log.error("消息ID{}消费失败，已重试次数{}！开始重试...", messageId, retryCount, e);
            message.getMessageProperties().setHeader("errorMessage", e.getMessage());
            //如果已经大于等于最大重试次数了，直接将消息丢入死信队列
            if (retryCount >= maxAttempts) {
                try {
                    //由于运维系统不靠谱所以不完全依赖消息队列了
                    errorCallback.accept(e.getMessage());
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                } catch (IOException ex) {
                    log.error("手动确认：", ex);
                }
            } else {
                retryCount = retryCount + 1;
                redisUtil.set(rabbitRetryPreKey + messageId, retryCount, 60 * 60 * 12);
                //重试间隔时间乘积
                long interval = initialInterval + initialInterval * ((long) multiplier * (retryCount - 1));
                //重试间隔时间
                long retryInterval = Math.min(interval, maxInterval);
                log.info("消息ID：{}，重试次数：{}，重试间隔时间：{}，最大间隔时间：{}", messageId, retryCount, retryInterval, maxInterval);
                //睡眠一会儿，重新放入队列
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException ex) {
                    log.error(" 重试休眠：", ex);
                }
                try {
                    //重入队列之前先将消费消息删掉
                    String produceApplication = RabbitUtils.getProduceApplication(message);
                    consumerRecordManager.deleteRecord(produceApplication, applicationName, messageId);
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
                } catch (IOException ex) {
                    log.error("手动确认：", ex);
                }
            }
            throw new AmqpException("消费异常：", e);
        }
    }

    public void retry(Message message, Channel channel, Runnable runnable, Consumer<String> errorCallback) {
        this.retry(message, channel, defaultInitialInterval, defaultMultiplier, defaultMaxInterval, defaultMaxAttempts, runnable, errorCallback);
    }

}
