package com.meijm.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class WorkStateMachine extends EnumStateMachineConfigurerAdapter<States, Events> {

    /**
     * 配置状态机监听
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true).listener(listener());
    }

    /**
     * 配置状态机初始值及范围
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S1)
                .end(States.S3)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * 配置状态机转换
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.S1).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S3).event(Events.E2)
                .and()
                .withExternal()
                .source(States.S2).target(States.S1).event(Events.E1);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if(from!=null){
                    System.out.println("State change from "+from.getId()+" to " + to.getId());
                }else{
                    System.out.println("State change  to " + to.getId());
                }
            }
        };
    }
}
