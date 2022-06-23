package com.meijm.activiti.config.listener.custom;

import lombok.Data;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.stereotype.Service;

@Data
@Service("executionStartListener")
public class ExecutionStartListener implements ExecutionListener {

    protected Expression start;

    @Override
    public void notify(DelegateExecution execution) {
        String startText = start.getExpressionText();
        System.out.println(startText);
    }
}
