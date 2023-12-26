package com.meijm.springstream.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component   
public class MySink {

    @Autowired
    private Sink sink;

    @StreamListener(Sink.INPUT)
    public void handle(Message<String> message) {
        System.out.println("Received: " + message.getPayload());    
    }

}