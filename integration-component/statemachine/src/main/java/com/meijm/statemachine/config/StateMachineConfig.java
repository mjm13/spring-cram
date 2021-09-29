package com.meijm.statemachine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

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