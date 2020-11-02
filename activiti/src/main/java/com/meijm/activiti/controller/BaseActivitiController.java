package com.meijm.activiti.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "baseActiviti")
public class BaseActivitiController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    //发起一个流程
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public ProcessInstance start() {
        //mjm 为任意值,表示与业务关联的信息,
        return runtimeService.startProcessInstanceByKey("SimpleProcess", "mjm");
    }

    //查看指定用户任务列表
    @RequestMapping(value = "taskList", method = RequestMethod.GET)
    public List<Map<String,Object>> taskList() {
        //mjm 为流程定义中分配的人员 对应 processes/SimpleProcess.bpmn20.xml:5
        List<Task> tasks =  taskService.createTaskQuery().taskAssignee("admin").list();
        return tasks.stream().map(task -> {
            Map<String,Object> data = new HashMap<>();
            data = BeanUtil.copyProperties(task,Map.class,"execution","processInstance","variableInstances");
            return data;
        }).collect(Collectors.toList());
    }

    //查看指定用户任务列表
    @RequestMapping(value = "completeTask", method = RequestMethod.GET)
    public String completeTask(String processId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processId).taskAssignee("admin").list();
        if (CollectionUtil.isEmpty(taskList)) {
            throw new ActivitiObjectNotFoundException("processId:" + processId + "-Assignee:admin");
        }
        taskService.complete(taskList.get(0).getId());
        //mjm 为流程定义中分配的人员 对应 processes/SimpleProcess.bpmn20.xml:5
        return "操作完成!";
    }

}
