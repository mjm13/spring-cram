package com.meijm.rabbitmq.manual.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RestController
public class SendController {
    @Autowired
    private Channel channel;

    /**
     * 创建消息监听
     */
    @PostConstruct
    public void init(){
        long a = 945026673617801216L;
        
        try {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message: " + message);
            };
            // 将监听器添加到队列
            channel.basicConsume("temp-queue", true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * http://localhost:8080/send?message=bbb 
     * 发送消息示例
     * @param message
     * @return
     */
    @GetMapping("/send")
    public String sendMessage(String message){
        try {
            channel.basicPublish("temp-exchange","temp-queue",null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Send Over";
    }
}
