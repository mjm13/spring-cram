package com.meijm.udp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
public class UdpConfig {
    @Value("${udp.port}")
    private Integer udpPort;

    @Bean
    public IntegrationFlow processUniCastUdpMessage(MessageHandler udpClient) {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(udpPort))
                .handle(udpClient)
                .get();
    }

    @Bean
    public UnicastSendingMessageHandler sending(){
        return new UnicastSendingMessageHandler("localhost", udpPort);
    }
}
