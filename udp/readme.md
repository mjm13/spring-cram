# Spring Boot 监听UDP消息
## 依赖配置
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-integration</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-ip</artifactId>
        </dependency>
```
## java配置
```java

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
```
## 使用
**监听消息:** udpClient名称对应配置中bean
```java
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
```

**发送消息**
```java
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
```