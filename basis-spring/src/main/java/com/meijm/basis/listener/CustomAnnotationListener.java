package com.meijm.basis.listener;

import cn.hutool.json.JSONUtil;
import com.meijm.basis.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAnnotationListener {

    /**
     * 同步监听加条件判断
     * @param event
     */
    @EventListener(condition = "#event.type eq 'annotation' ")
    public void listenCustomAnnotationEvent1(CustomAnnotationEvent event) {
        log.info("listenCustomAnnotationEvent1:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * 同步监听加条件判断及异常
     * @param event
     */
    @EventListener(condition = "#event.type eq 'error' ")
    public void listenCustomAnnotationErrorEvent(CustomAnnotationEvent event) {
        log.info("listenCustomAnnotationEvent1:{}", JSONUtil.toJsonStr(event));
        throw new RuntimeException("异常测试1");
    }


    @EventListener(condition = "#event.object != null and '1' eq event.object.get('type') and '2' eq event.object.get('mark') ")
    public void listenCustomObjectEvent(CustomObjectEvent event) {
        log.info("listenCustomObjectEvent:{}", JSONUtil.toJsonStr(event));
    }


    /**
     * 异步监听加条件判断
     * @param event
     */
    @EventListener
    @Async
    public void listenCustomAsyncEvent(CustomAsyncEvent event) {
        log.info("listenCustomAnnotationEvent2:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * @param event
     */
    @EventListener(condition = "#event.type eq 'anycAnnotation' ")
    @Async
    public void listenCustomAnnotationAsyncEvent(CustomAnnotationEvent event) {
        log.info("listenCustomAnnotationEvent1:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * 异步监听加条件判断及异常
     * @param event
     */
    @EventListener
    @Async
    public void listenCustomAsyncErrorEvent(CustomAsyncErrorEvent event) {
        log.info("listenCustomAsyncErrorEvent:{}", JSONUtil.toJsonStr(event));
        throw new RuntimeException("异常测试2");
    }
    /**
     * 不会过滤已捕获的事件
     * @param event
     */
    @EventListener
    public void listenCustomAnnotationEventAll(CustomAnnotationEvent event) {
        log.info("listenCustomAnnotationEventAll:{}", JSONUtil.toJsonStr(event));
    }

    @EventListener(classes = { CustomAnnotationEvent.class, CustomAsyncErrorEvent.class,CustomAsyncEvent.class, CustomMetohEvent.class})
    public void handleMultipleEvents(ApplicationEvent event) {
        log.info("handleMultipleEvents:{}", JSONUtil.toJsonStr(event));
    }
}
