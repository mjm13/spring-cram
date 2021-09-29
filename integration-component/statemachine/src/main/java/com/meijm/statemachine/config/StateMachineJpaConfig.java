/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meijm.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

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
