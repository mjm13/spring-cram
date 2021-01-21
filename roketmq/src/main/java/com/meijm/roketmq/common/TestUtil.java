package com.meijm.roketmq.common;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static Message<Map<String, String>> getMessage(String value){
        Map<String, String> data = new HashMap<>();
        data.put("key", value);
        return MessageBuilder.withPayload(data).
                build();
    }
}
