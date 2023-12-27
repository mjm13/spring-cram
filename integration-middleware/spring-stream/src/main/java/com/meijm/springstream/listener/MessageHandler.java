package com.meijm.springstream.listener;

import com.meijm.springstream.component.MyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MyProcessor.class)
public class MessageHandler {

    @StreamListener(MyProcessor.INPUT)
    public void handleMessage(@Payload String message) {
        System.out.println("Received message: " + message);
    }

    @Autowired
    private MyProcessor myProcessor;
    
    public void sendMessage(String payload) {
        MessageChannel outputChannel = myProcessor.myOutput();
        Message<String> message = MessageBuilder.withPayload(payload).build();
        outputChannel.send(message);
    }
}
