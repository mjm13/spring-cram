package com.meijm.basis.listener.spring;

import com.meijm.basis.annotation.DynamicMapping;
import com.meijm.basis.annotation.DynamicMappingUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Map;

/**
 * 测试动态注册链接
 */
@Slf4j
@Component
public class DynamicMappingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("注册日志：{}", event.getSource().getClass().toString());
        
        handlerMapping.getHandlerMethods().
                entrySet().stream().
                filter(entry -> AnnotationUtils.getAnnotation(entry.getValue().getMethod(), DynamicMapping.class) != null).
                forEach(entry -> {
                    try {
                        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
                        options.setPatternParser(new PathPatternParser());
                        String path = entry.getKey().getDirectPaths().stream().findFirst().orElse(null);
                        String beanName = (String) entry.getValue().getBean();
                        DynamicMappingUrl dmu = new DynamicMappingUrl(entry.getValue().getMethod(),event.getApplicationContext().getBean(beanName));
                        RequestMappingInfo info = RequestMappingInfo
                                .paths(path+"_mjm")
                                .methods(RequestMethod.GET)
                                .options(options)
                                .build();
                        handlerMapping.registerMapping(info,dmu,DynamicMappingUrl.class.getMethod("addMapping", Map.class));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}