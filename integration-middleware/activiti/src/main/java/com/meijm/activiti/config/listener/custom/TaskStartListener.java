package com.meijm.activiti.config.listener.custom;

import lombok.Data;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Data
@Service("taskStartListener")
public class TaskStartListener implements TaskListener {

    protected Expression url;

    public void notify(DelegateTask delegateTask) {
        String urlText = url.getExpressionText();
        System.out.println(urlText);
    }


}

