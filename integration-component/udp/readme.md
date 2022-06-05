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
* spring-boot-starter-integration：EIP(Enterprise Integration Pattern企业集成模式)的spring实现，主要用于各种消息类型(MQ,TCP/UDP,Redis,JPA,HTTP等)的交互与编排。
  Spring Cloud Stream就是基于spring-integration实现的。
* spring-integration-ip：针对基于TCP/UDP协议的数据传输功能实现。org.springframework.integration下包含各种数据源及协议的实现，例如spring-integration-redis,spring-integration-jpa，

> ESB就是基于EIP概念实现的针对企业消息管理

> 下面的示例并没有体现出integration的消息编排功能，只是在服务上监听UDP消息
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
                .from(new UnicastReceivingChannelAdapter(udpPort,true))
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
        return new UnicastSendingMessageHandler("localhost", udpPort,true);
    }
}
```
* processUniCastUdpMessage：配置数据处理流，其中IntegrationFlows为Integration核心配置，from指定数据来源，handle指定处理类。
* sending：配置的是消息发送类，用于测试上面配置的IntegrationFlows。
* UnicastReceivingChannelAdapter:用于接收UDP的适配器，支持长度检查,开启则会检查消息长度，长度存储于消息体字节码前4位。
* UnicastSendingMessageHandler：用于配置发送UDP消息的处理类，指定接收ip，端口，长度检查，开启长度检查则会在发送之前将消息的长度设置到消息转换的byte数组前4位与接收器中位置对应。

> 其它非spring-integration整合的消息发送方开启长度检查则需要注意消息长度信息存储的位置。

## 使用
**监听消息:** udpClient名称对应配置中MessageHandler udpClient
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
        String payload = null;
        try {
            payload = new String((byte[]) message.getPayload(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("接收UDP消息-payload:{}", payload);
    }
}
```
> 由于开启了消息检查接收时需指定编码，否则接收到的数据为乱码。

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
        log.info("发送UDP消息: {}", message);
        sendingMessageHandler.handleMessage(MessageBuilder.withPayload(message).build());
    }
}
```

# 参考资料

https://docs.spring.io/spring-integration/docs/5.3.10.RELEASE/reference/html/
