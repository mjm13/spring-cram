
# Activiti监听任务的几种方式
## 全局监听
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
* onEvent：事件处理代码
* isFailOnException：出现异常时当前操作是否失败
* TASK_COMPLETED:为org.activiti.engine.delegate.event.ActivitiEventType枚举值。通过查看引用可查看抛出事件携带的参数，便于从event中获取携的参数。
> 源码ActivitiEventSupport.dispatchEvent中使用isFailOnException返回值来判断是否继续抛出异常

Spring 配置类
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
* ProcessEngineConfigurationConfigurer：为activiti-spring-boot提供的activiti配置入口，继承重写configure方法即可设置activiti配置。
*  activitiEventListeners注入说明：Spring集合注入会将Spring中托管的子类都注入此集合

## TaskListener监听
通过bpmn文件配置
```xml
    <userTask id="任务id" name="任务名称" activiti:candidateUsers="候选用户">
      <extensionElements>
        <activiti:taskListener event="create" delegateExpression="${taskStartListener}">
          <activiti:field name="url" stringValue="333"/>
        </activiti:taskListener>
      </extensionElements>
    </userTask>
```
* delegateExpression：代理表达式，支持读取Spring托管的bean
* event ： 监听的节点assignment/设置代理人，complete/任务完成，create/创建任务
* activiti:field 监听类扩展参数，对应类中使用Expression读取配置文件中指定内容，除了字符串还可以指定为动态表达式例如：expression="Hello ${gender == 'male' ? 'Mr.' : 'Mrs.'} ${name}",表达式中可访问的内容包含流程变量及spring中定义的bean

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
## ExecutionListener监听
通过bpmn文件配置
```xml
    <userTask id="任务id" name="任务名称" activiti:candidateUsers="候选用户">
    <extensionElements>
        <activiti:executionListener event="start" delegateExpression="${executionStartListener}">
          <activiti:field name="start" stringValue="start"/>
        </activiti:executionListener>
    </extensionElements>
</userTask>
```
executionListener： 执行监听，可用于监听流程，环节，连接线，比任务监听范围更广但没有assignment这种针对任务的监听
* delegateExpression：代理表达式，支持读取Spring托管的bean
* event ： 监听的节点start/节点开始，end/节点结束，take/监听连接线
* activiti:field 监听类扩展参数

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
