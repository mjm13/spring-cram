/*
 * Copyright 2019 the original author or authors.
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
package com.meijm.statemachine.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.data.StateMachineRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StateMachineController {


	@Autowired
	private StateMachineService<String, String> stateMachineService;

	@Autowired
	private StateMachineRepository stateMachineRepository;

	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("redirect:/list");
	}

	@RequestMapping("/list")
	public ModelAndView list(Model model) {
		List<JpaRepositoryStateMachine> stateMachines = Lists.newArrayList(stateMachineRepository.findAll());
		model.addAttribute("stateMachines",stateMachines);
		return new ModelAndView("states");
	}
	@RequestMapping("/add")
	public ModelAndView add(String machineId ,Model model) {
		stateMachineService.acquireStateMachine(machineId);
		return new ModelAndView("redirect:/list");
	}

	@RequestMapping("/commute")
	public ModelAndView commute(String machineId ,Model model) {
		StateMachine<String, String> stateMachine =  stateMachineService.acquireStateMachine(machineId);
		Message<String> event = MessageBuilder.withPayload("通勤").setHeader("machineId", machineId).build();
		stateMachine.sendEvent(event);
		stateMachineService.releaseStateMachine(machineId);
		return new ModelAndView("redirect:/list");
	}

}
