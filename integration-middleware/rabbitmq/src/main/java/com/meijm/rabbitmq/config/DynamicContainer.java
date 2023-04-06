package com.meijm.rabbitmq.config;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
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


    @PostConstruct
    public void init() {
        this.setConnectionFactory(connectionFactory);
        this.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        this.setDefaultRequeueRejected(false);
        RabbitProperties.SimpleContainer config = rabbitProperties.getListener().getSimple();
        RabbitProperties.ListenerRetry retryConfig = config.getRetry();
        if (retryConfig.isEnabled()) {
//            RetryInterceptorBuilder builder = RetryInterceptorBuilder.stateless();
//            builder.maxAttempts(5);
//            builder.backOffOptions(1000, 1, 10000);
//            Advice[] adviceChain = {builder.build()};
//            this.setAdviceChain(adviceChain);
//            this.setAdviceChain(RetryInterceptorBuilder
//                    .stateless()
//                    .recoverer(new RejectAndDontRequeueRecoverer())
//                    .retryOperations(retryTemplate)
//                    .build());
            this.setAdviceChain(RetryInterceptorBuilder.stateful()
                    .recoverer(new RejectAndDontRequeueRecoverer())
                    .backOffOptions(1000, 1.0, 5000)
                    .maxAttempts(5).build());
        }

//    @PostConstruct
//    public void init() {
//        this.setConnectionFactory(connectionFactory);
//        this.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        this.setDefaultRequeueRejected(false);
//        RabbitProperties.SimpleContainer config = rabbitProperties.getListener().getSimple();
//        RabbitProperties.ListenerRetry retryConfig = config.getRetry();
//        if (retryConfig.isEnabled()) {
////            RetryInterceptorBuilder builder = RetryInterceptorBuilder.stateless();
////            builder.maxAttempts(5);
////            builder.backOffOptions(1000, 1, 10000);
////            Advice[] adviceChain = {builder.build()};
////            this.setAdviceChain(adviceChain);
////            this.setAdviceChain(RetryInterceptorBuilder
////                    .stateless()
////                    .recoverer(new RejectAndDontRequeueRecoverer())
////                    .retryOperations(retryTemplate)
////                    .build());
//            this.setAdviceChain(RetryInterceptorBuilder.stateful()
//                    .recoverer(new RejectAndDontRequeueRecoverer())
//                    .backOffOptions(1000, 1.0, 5000)
//                    .maxAttempts(5).build());
//        }
    }
}
