package com.meijm.udp.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UdpServer {

    @Autowired
    private UnicastSendingMessageHandler sendingMessageHandler;

    public void sendMessage(String message) {
        log.info("发送UDP: {}", message);
        sendingMessageHandler.handleMessage(MessageBuilder.withPayload(message).build());
    }
}