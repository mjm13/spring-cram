package com.meijm.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
//import org.springframework.boot.autoconfigure.amqp.RetryTemplateFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Scope("prototype")
@Component
public class DynamicContainer extends SimpleMessageListenerContainer {
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private RabbitProperties rabbitProperties;
    @Autowired
    private RetryTemplate retryTemplate;
    @Autowired
    private DynamicMessageRecoverer recoverer;

    @PostConstruct
    public void init(){
        this.setConnectionFactory(connectionFactory);
        this.setAcknowledgeMode(AcknowledgeMode.AUTO);
        RabbitProperties.SimpleContainer config = rabbitProperties.getListener().getSimple();
        RabbitProperties.ListenerRetry retryConfig = config.getRetry();
        if (retryConfig.isEnabled()) {
            RetryInterceptorBuilder<?, ?> builder = RetryInterceptorBuilder.stateless();
            builder.retryOperations(retryTemplate);
            builder.recoverer(recoverer);
            this.setAdviceChain(builder.build());
        }
    }
}
