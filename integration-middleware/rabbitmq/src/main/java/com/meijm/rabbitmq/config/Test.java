package com.meijm.rabbitmq.config;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Test extends ConditionalRejectingErrorHandler {
    @Override
    public void handleError(Throwable t) {
        System.out.println(1232412);
        super.handleError(t);
    }
}
