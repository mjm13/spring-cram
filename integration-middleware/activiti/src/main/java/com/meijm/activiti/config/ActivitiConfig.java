package com.meijm.activiti.config;


import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    @Lazy
    @Autowired
    private List<ActivitiEventListener> activitiEventListeners;

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setEventListeners(activitiEventListeners);
        //暂时关闭定时任务
        processEngineConfiguration.setAsyncExecutorActivate(false);
    }

}