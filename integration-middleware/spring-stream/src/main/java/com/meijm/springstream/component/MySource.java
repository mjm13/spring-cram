package com.meijm.springstream.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MySource {
    
    @Autowired
    private Source source;

    
    public void send(String message) {
        Message<String> message1 = MessageBuilder.withPayload(message).build();
        source.output().send(message1);
    }
}