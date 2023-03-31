package com.prolog.rdc.outbound.mq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;

@Configuration("RabbitmqConfiguration")
public class RabbitmqConfiguration {

    @Primary
    @Bean("sb_RabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         ObjectProvider<MessageConverter> messageConverter1, RabbitProperties properties1) {
        PropertyMapper map = PropertyMapper.get();
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        MessageConverter messageConverter = messageConverter1.getIfUnique();
        if (messageConverter != null) {
            template.setMessageConverter(messageConverter);
        }
        template.setMandatory(determineMandatoryFlag(properties1));
        RabbitProperties.Template properties = properties1.getTemplate();
        if (properties.getRetry().isEnabled()) {
            template.setRetryTemplate(createRetryTemplate(properties.getRetry()));
        }
        map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis).to(template::setReceiveTimeout);
        map.from(properties::getReplyTimeout).whenNonNull().as(Duration::toMillis).to(template::setReplyTimeout);
        map.from(properties::getExchange).to(template::setExchange);
        map.from(properties::getRoutingKey).to(template::setRoutingKey);
        return template;
    }

    private boolean determineMandatoryFlag(RabbitProperties properties) {
        Boolean mandatory = properties.getTemplate().getMandatory();
        return (mandatory != null) ? mandatory : properties.isPublisherReturns();
    }

    private RetryTemplate createRetryTemplate(RabbitProperties.Retry properties) {
        PropertyMapper map = PropertyMapper.get();
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
        template.setRetryPolicy(policy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
                .to(backOffPolicy::setInitialInterval);
        map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
        map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis).to(backOffPolicy::setMaxInterval);
        template.setBackOffPolicy(backOffPolicy);
        return template;
    }
}