package com.meijm.basis.listener;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * 标准事件
 */
@Slf4j
@Component
public class StandardEventListenner {

    /**
     * ApplicationContext初始化或刷新时发布
     * @param event
     */
    @EventListener
    public void listenContextRefreshedEvent(ContextRefreshedEvent event) {
        log.info("listenContextRefreshedEvent:{}", JSONUtil.toJsonStr(event));
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void listenContextStartedEvent(ContextStartedEvent event) {
        log.info("listenContextStartedEvent:{}", JSONUtil.toJsonStr(event));
    }

    @EventListener
    public void listenContextStoppedEvent(ContextStoppedEvent event) {
        log.info("listenContextStoppedEvent:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * 服务关闭事件
     * @param event
     */
    @EventListener
    public void listenContextClosedEvent(ContextClosedEvent event) {
        log.info("listenContextClosedEvent:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * 请求处理完成事件
     * @param event
     */
    @EventListener
    public void listenRequestHandledEvent(RequestHandledEvent event) {
        log.info("listenRequestHandledEvent:{}", JSONUtil.toJsonStr(event));
    }

    /**
     * 捕获请求事件
     * @param event
     */
    @EventListener
    public void listenServletRequestHandledEvent(ServletRequestHandledEvent event) {
        log.info("listenServletRequestHandledEvent:{}", JSONUtil.toJsonStr(event));
    }
}
