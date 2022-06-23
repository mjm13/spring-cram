package com.meijm.activiti.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.meijm.activiti.common.flow.RejectTask;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "baseActiviti")
public class BaseActivitiController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private ManagementService managementService;

    /**
     * @Description
     * @Author MeiJM
     * @Date 2022/6/20
     * @param processDefinitionKey  M2022Defined
     * @param businessKey       业务标识
     * @return java.util.Map<java.lang.String, java.lang.Object>
     **/
    //发起一个流程
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public Map<String, Object> start(String processDefinitionKey,String businessKey) {
        //mjm businessKey为任意值,表示与业务关联的信息,便于集成
        Authentication.setAuthenticatedUserId("admin");
        processDefinitionKey = StrUtil.blankToDefault(processDefinitionKey,"SimpleProcess_id");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
        return BeanUtil.copyProperties(processInstance, Map.class, "currentFlowElement", "subProcessInstance", "identityLinks", "executions");
    }

    //查看指定用户任务列表
    @RequestMapping(value = "taskList", method = RequestMethod.GET)
    public List<Map<String, Object>> taskList() {
        //mjm 为流程定义中分配的人员 对应 processes/SimpleProcess.bpmn:5
        List<Task> tasks = taskService.createTaskQuery().list();
        historyService.createHistoricTaskInstanceQuery().list();
        return tasks.stream().map(task -> {
            Map<String, Object> data = new HashMap<>();
            data = BeanUtil.copyProperties(task, Map.class, "execution", "processInstance", "variableInstances");
            return data;
        }).collect(Collectors.toList());
    }

    @Autowired
    private HistoryService historyService;

    //完成任务
    @RequestMapping(value = "completeTask", method = RequestMethod.GET)
    public String completeTask(String processId) {
        Authentication.setAuthenticatedUserId("王五");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processId).list();
        if (CollectionUtil.isEmpty(taskList)) {
            throw new ActivitiObjectNotFoundException("processId:" + processId + "-Assignee:admin");
        }
        taskService.complete(taskList.get(0).getId());
        //mjm 为流程定义中分配的人员 对应 processes/SimpleProcess.bpmn:5
        return "操作完成!";
    }

    @RequestMapping(value = "rejectTask", method = RequestMethod.GET)
    public String rejectTask(String processId) {
        Authentication.setAuthenticatedUserId("王五");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processId).list();
        if (CollectionUtil.isEmpty(taskList)) {
            throw new ActivitiObjectNotFoundException("processId:" + processId + "-Assignee:admin");
        }
        managementService.executeCommand(new RejectTask(taskList.get(0).getId(),new HashMap<>(),"驳回"));
        return "操作完成!";
    }



}
