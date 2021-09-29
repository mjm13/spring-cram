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
    /**
     * udp传输端口
     */
    @Value("${udp.port}")
    private Integer udpPort;


    /**
     * 接收的消息配置
     * 指定接收的端口
     * @param udpClient
     * @return
     */
    @Bean
    public IntegrationFlow processUniCastUdpMessage(MessageHandler udpClient) {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(udpPort))
                .handle(udpClient)
                .get();
    }

    /**
     * 发送消息配置
     * 指定ip和端口
     * @return
     */
    @Bean
    public UnicastSendingMessageHandler sending(){
        return new UnicastSendingMessageHandler("localhost", udpPort);
    }
}
