package com.meijm.activiti.config.listener.global;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityWithVariablesEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.activiti.engine.delegate.event.ActivitiEventType.TASK_COMPLETED;
import static org.activiti.engine.delegate.event.ActivitiEventType.TASK_CREATED;

@Slf4j
@Component
public class TaskCompletedEventListener implements ActivitiEventListener {


    @Override
    public void onEvent(ActivitiEvent event) {
        if (!event.getType().equals(TASK_CREATED)) {
            return;
        }
        System.out.println("111");
        ActivitiEntityWithVariablesEventImpl activitiEntityEvent = (ActivitiEntityWithVariablesEventImpl) event;
        TaskEntity taskEntity = (TaskEntity) activitiEntityEvent.getEntity();
        Map data = activitiEntityEvent.getVariables();

    }

    @Override
    public boolean isFailOnException() {
        return true;
    }
}
