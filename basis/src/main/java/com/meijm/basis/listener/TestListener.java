package com.meijm.basis.listener;

import com.meijm.basis.common.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestListener {

    @EventListener(condition = "#testEvent.type eq 'test1' ")
    public void listenTestEvent1(TestEvent testEvent) {
        log.info("listenTestEvent1:{}",testEvent.getType());
    }

    @EventListener(condition = "#testEvent.type eq 'test2' ")
    public void listenTestEvent2(TestEvent testEvent) {
        log.info("listenTestEvent2:{}",testEvent.getType());
    }

    @EventListener
    public void listenTestEvent3(TestEvent testEvent) {
        log.info("listenTestEvent3:{}",testEvent.getType());
    }
}
