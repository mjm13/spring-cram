# Spring ApplicationEvent 使用
ApplicationEvent为Spring的事件基类,可通过@EventListener或实现ApplicationListener接口进行监听

## 监听及触发事件
**自定义事件**

```java
import org.springframework.context.ApplicationEvent;

public class CustomAnnotationEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4180050946434009635L;
    /**
     * 类型
     */
    private String type;
    /**
     * 构造方法
     *
     * @param source 事件源
     * @param type   类型
     */
    public CustomAnnotationEvent(Object source, String type) {
        super(source);
        this.type = type;
    }
    /**
     * 获取类型
     *
     * @return 获取类型
     */
    public String getType() {
        return type;
    }
}
```

**抛出事件**

```java
//spring注入
@Autowired
private ApplicationContext applicationContext;
...
CustomAnnotationEvent event = new CustomAnnotationEvent(this, "annotation");
applicationContext.publishEvent(event);
```

**监听事件**

```java
@EventListener
public void listenCustomAnnotationEventAll(CustomAnnotationEvent event) {
    log.info("listenCustomAnnotationEventAll:{}", JSONUtil.toJsonStr(event));
}
```

> 监听事件及抛出事件的类需为spring管理的bean

## 其它

```java
@EventListener(condition = "#event.type eq 'anycAnnotation' ")
@Async
public void listenCustomAnnotationAsyncEvent(CustomAnnotationEvent event) {
	log.info("listenCustomAnnotationEvent1:{}", JSONUtil.toJsonStr(event));
}
```

* **异步事件:**在方法上增加@Async注解则会将事件处理转为异步处理,异常及耗时不会影响抛出事件的方法,需在启动类中增加@EnableAsync开启此功能

* **条件过滤:**@EventListener注解中condition为SpEL表达式,可访问参数中的属性进行判断是否处理此事件

* **同时监听多个事件:**可使用@EventListene注解中classes条件扩充监听的事件

```java
@EventListener(classes = { CustomAnnotationEvent.class, CustomAsyncErrorEvent.class,CustomAsyncEvent.class, CustomMetohEvent.class})
public void handleMultipleEvents(ApplicationEvent event) {
    log.info("handleMultipleEvents:{}", JSONUtil.toJsonStr(event));
}
```



## 标准事件

| 事件类                | 说明                                 |
| --------------------- | ------------------------------------ |
| ContextRefreshedEvent | ApplicationContext初始化或刷新时发布 |
| ContextStartedEvent   | 手动调用start()方法时发布            |
| ContextStoppedEvent   | 手动调用stop()方法时发布             |
| ContextClosedEvent    | ApplicationContext关闭时发布         |

> 另还有很多内置事件可通过查看ApplicationEvent子类来查看,例如SessionCreationEvent,KafkaEvent,RedisKeyspaceEvent等

## 参考资料

https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-events

https://www.baeldung.com/spring-context-events
