package com.meijm.activiti.common.flow;

import cn.hutool.core.collection.CollectionUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.HistoricTaskInstanceQueryProperty;
import org.activiti.engine.impl.cmd.DeleteProcessInstanceCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.task.IdentityLinkType;

import java.util.List;
import java.util.Map;

public class RejectTask implements Command<Void> {

    private String taskId;
    private String rejectReason;
    private Map<String, Object> variables;

    public RejectTask(String taskId, Map<String, Object> variables, String rejectReason) {
        this.taskId = taskId;
        this.variables = variables;
        this.rejectReason = rejectReason;
    }


    @Override
    public Void execute(CommandContext commandContext) {
        TaskEntity task = commandContext.getTaskEntityManager().findById(taskId);
        HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = commandContext.getHistoricTaskInstanceEntityManager();
        HistoricTaskInstanceQueryImpl historicTaskInstanceQuery = new HistoricTaskInstanceQueryImpl();
        historicTaskInstanceQuery.processInstanceId(task.getProcessInstanceId());
        historicTaskInstanceQuery.orderBy(HistoricTaskInstanceQueryProperty.START).desc();
        List<HistoricTaskInstance> htiList = historicTaskInstanceEntityManager.findHistoricTaskInstancesByQueryCriteria(historicTaskInstanceQuery);

        if (CollectionUtil.isEmpty(htiList) || htiList.size() < 2) {
            DeleteProcessInstanceCmd cmd = new DeleteProcessInstanceCmd(task.getProcessInstanceId(), rejectReason);
            cmd.execute(commandContext);
        } else {
            // list里的第二条代表上一个任务
            HistoricTaskInstance lastTask = htiList.get(1);
            String processDefinitionId = lastTask.getProcessDefinitionId();
            BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionId);

            // 得到上个节点的开始位置
            FlowNode lastFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(lastTask.getTaskDefinitionKey());
            FlowElement begin = lastFlowNode.getIncomingFlows().get(0).getSourceFlowElement();

            commandContext.getProcessEngineConfiguration().getListenerNotificationHelper().executeTaskListeners(task, TaskListener.EVENTNAME_COMPLETE);
            if (Authentication.getAuthenticatedUserId() != null && task.getProcessInstanceId() != null) {
                ExecutionEntity processInstanceEntity = commandContext.getExecutionEntityManager().findById(task.getProcessInstanceId());
                commandContext.getIdentityLinkEntityManager().involveUser(processInstanceEntity, Authentication.getAuthenticatedUserId(), IdentityLinkType.PARTICIPANT);
            }

            ActivitiEventDispatcher eventDispatcher = Context.getProcessEngineConfiguration().getEventDispatcher();
            if (eventDispatcher.isEnabled()) {
                eventDispatcher.dispatchEvent(ActivitiEventBuilder.createEntityWithVariablesEvent(ActivitiEventType.TASK_COMPLETED, task, variables, false));
            }
            task.setVariables(variables);
            commandContext.getTaskEntityManager().deleteTask(task, rejectReason, false, false);
            ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(task.getExecutionId());
            executionEntity.setCurrentFlowElement(begin);
            commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
        }
        return null;
    }

}
