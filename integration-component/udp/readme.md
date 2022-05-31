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
* spring-boot-starter-integration：EIP(Enterprise Integration Pattern企业集成模式)的spring实现，主要用于各种消息类型的交互与编排。
  Spring Cloud Stream就是基于spring-integration实现的。
* spring-integration-ip：针对基于TCP/UDP协议的数据传输功能实现。org.springframework.integration下包含各种数据源及协议的实现，例如spring-integration-redis,spring-integration-jpa，

> ESB就是基于EIP概念实现的针对企业消息管理

> 下面的示例并没有使用integration的消息编排功能，只是在服务上监听UDP消息
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

**发送测试消息**
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

# 参考资料

https://docs.spring.io/spring-integration/docs/5.3.10.RELEASE/reference/html/