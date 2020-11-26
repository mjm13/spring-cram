# 状态机简介
状态机是一种用来进行对象行为建模的工具，其作用主要是描述对象在它的生命周期内所经历的状态序列，以及如何响应来自外界的各种事件,摘录自这个大神的文章[状态机引擎选型](https://segmentfault.com/a/1190000009906317)

# 示例说明
网上能找到的示例大多都过于复杂,所以写了一个简单的示例下面的代码定义了一个极简的状态机,仅包含两个状态(上班,下班),一个事件(通勤),通过web页面操作添加状态机及状态转换,集成了spring-statemachine-data-jpa进行持久化. 

# SpringBoot2.x配置说明
* Pom.xml
``` xml
        <dependency>
            <groupId>org.springframework.statemachine</groupId>
            <artifactId>spring-statemachine-starter</artifactId>
            <version>2.2.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.statemachine</groupId>
            <artifactId>spring-statemachine-data-jpa</artifactId>
            <version>2.2.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```
## java配置
* 状态机持久化
``` java
@Configuration
public class StateMachineJpaConfig {

    /**
     * StateMachineRuntimePersister为状态机运行时持久化配置
     * @param jpaStateMachineRepository
     * @return
     */
    @Bean
    public StateMachineRuntimePersister<String, String, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    /**
     * StateMachineService为状态状态机持久化控制,用于获取或关闭状态机
     * @param stateMachineFactory
     * @param stateMachineRuntimePersister
     * @return
     */
    @Bean
    public StateMachineService<String, String> stateMachineService(
            StateMachineFactory<String, String> stateMachineFactory,
            StateMachineRuntimePersister<String, String, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<String, String>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
```
* 状态机监听
继承StateMachineListenerAdapter重写需监听的方法即可,通过方法名可推测监听的事件,就不贴代码了.

* 状态机配置,@EnableStateMachine为单例模式并不适合此示例所以使用@EnableStateMachineFactory
``` java
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {


    @Autowired
    private StateMachineRuntimePersister<String, String, String> stateMachineRuntimePersister;

    @Autowired
    private StateMachineLogListener logListener;

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister)
                .and()
                .withConfiguration().listener(logListener);
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("宿舍")
                .state("公司");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("宿舍").target("公司")
                .event("通勤")
                .and()
                .withExternal()
                .source("公司").target("宿舍")
                .event("通勤");
    }
}
```
# 示例代码

https://gitee.com/MeiJM/spring-cram/tree/master/statemachine

# 参考资料
https://docs.spring.io/spring-statemachine/docs/2.2.0.RELEASE/reference/#preface

https://segmentfault.com/a/1190000009906317

