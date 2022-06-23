# 全局监听
监听类
```java
@Component
public class TaskCompletedEventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {
        if (!event.getType().equals(TASK_COMPLETED)) {
            return;
        }
        //监听处理
    }

    @Override
    public boolean isFailOnException() {
        return true;
    }
}
```
配置类
```java
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {
    @Lazy
    @Autowired
    private List<ActivitiEventListener> activitiEventListeners;
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setEventListeners(activitiEventListeners);
    }

}
```
# 通过bpmn文件配置taskListener监听
bpmn关键配置
```xml
    <userTask id="任务id" name="任务名称" activiti:candidateUsers="候选用户">
      <extensionElements>
        <activiti:taskListener event="create" delegateExpression="${taskStartListener}">
          <activiti:field name="url" stringValue="333"/>
        </activiti:taskListener>
      </extensionElements>
    </userTask>
```
```java
@Data
@Service("taskStartListener")
public class TaskStartListener implements TaskListener {
    protected Expression textExp;
    public void notify(DelegateTask delegateTask) {
        String text = textExp.getExpressionText();
        //任务开始监听
    }
}
```
# 通过bpmn文件配置executionListener监听
```xml
    <userTask id="任务id" name="任务名称" activiti:candidateUsers="候选用户">
    <extensionElements>
        <activiti:executionListener event="start" delegateExpression="${executionStartListener}">
          <activiti:field name="start" stringValue="start"/>
        </activiti:executionListener>
    </extensionElements>
</userTask>
```
```java
@Data
@Service("executionStartListener")
public class ExecutionStartListener implements ExecutionListener {

    protected Expression textExp;

    @Override
    public void notify(DelegateExecution execution) {
        String text = textExp.getExpressionText();
        //执行开始监听
    }
}
```
# 参考资料

https://www.activiti.org/userguide/