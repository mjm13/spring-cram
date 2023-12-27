package com.meijm.springstream.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyProcessor {
    String INPUT = "myInput";
    String OUTPUT = "myOutput";  // 新增输出通道

//    @Input(INPUT)
    @Input(INPUT)
    SubscribableChannel myInput();

    @Output(OUTPUT)
    MessageChannel myOutput();  // 输出通道
}
