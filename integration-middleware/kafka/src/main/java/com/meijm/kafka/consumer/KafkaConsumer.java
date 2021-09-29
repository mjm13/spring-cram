package com.meijm.kafka.consumer;

import com.meijm.kafka.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
//    @KafkaListener(topics = "test01",groupId = "group01")
//    public void listen(String message) {
//        log.info("message:{}",message);
//    }

//    @KafkaListener(topics = "custom04",containerFactory = "customContainerFactory")
//    public void customListen(TestVo message) {
//        log.info("message:{}",message.toString());
//    }

    @KafkaListener(topics = "custom04", groupId = "custom04Group")
    public void customListen(TestVo message) {
        log.info("message:{}", message.toString());
    }
}
