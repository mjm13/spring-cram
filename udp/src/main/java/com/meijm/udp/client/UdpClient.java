package com.meijm.udp.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("udpClient")
public class UdpClient implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = new String((byte[]) message.getPayload());
        log.info("接收到消息-payload:{}", payload);
    }
}
