package com.meijm.activiti.config.listener.global;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.springframework.stereotype.Component;

import static org.activiti.engine.delegate.event.ActivitiEventType.PROCESS_STARTED;

@Slf4j
@Component
public class ProcessStartEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent event) {
        if (!event.getType().equals(PROCESS_STARTED)) {
            return;
        }
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        ExecutionEntityImpl processInstanceEntity = (ExecutionEntityImpl) activitiEntityEvent.getEntity();
        String deploymentId = processInstanceEntity.getDeploymentId();

    }

    @Override
    public boolean isFailOnException() {
        return true;
    }
}
