package com.meijm.basis.listener;

import cn.hutool.json.JSONUtil;
import com.meijm.basis.event.CustomMetohEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomMetohListener implements ApplicationListener<CustomMetohEvent> {
    @Override
    public void onApplicationEvent(CustomMetohEvent event) {
        log.info("listenCustomMetohEvent:{}", JSONUtil.toJsonStr(event));
    }
}
