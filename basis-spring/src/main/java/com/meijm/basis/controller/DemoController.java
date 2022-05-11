package com.meijm.basis.controller;

import com.meijm.basis.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class DemoController {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 测试连接
     * http://localhost:8080/annotationEvent?type=annotation
     * http://localhost:8080/annotationEvent?type=annotation3
     * http://localhost:8080/annotationEvent?type=error
     * http://localhost:8080/annotationEvent?type=anycAnnotation
     * http://localhost:8080/annotationEvent?type=anyc
     * http://localhost:8080/annotationEvent?type=anycError
     * @param type
     * @return
     */
    @RequestMapping(value = "annotationEvent", method = RequestMethod.GET)
    public ApplicationEvent annotationEvent(String type) {
        ApplicationEvent event;
        switch (type) {
            case "anyc":
                event = new CustomAsyncEvent(this, type);
                break;
            case "anycError":
                event = new CustomAsyncErrorEvent(this, type);
                break;
            case "obj":
                Map<String,String> data = new HashMap<>();
                data.put("type","1");
                data.put("mark","2");
                event = new CustomObjectEvent(this, data);
                break;
            default:
                event = new CustomAnnotationEvent(this, type);
                break;
        }
        applicationContext.publishEvent(event);
        log.info("annotationEvent 发布结束");
        return event;
    }

    /**
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "methodEvent", method = RequestMethod.GET)
    public CustomMetohEvent methodEvent(String type) {
        CustomMetohEvent event = new CustomMetohEvent(this, type);
        applicationContext.publishEvent(event);
        log.info("methodEvent 发布结束");
        return event;
    }
}
