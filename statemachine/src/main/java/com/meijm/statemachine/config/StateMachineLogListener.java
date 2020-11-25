/*
 * Copyright 2018 the original author or authors.
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateContext.Stage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class StateMachineLogListener extends StateMachineListenerAdapter<String, String> {

	private  StateContext stateContext;

	@Override
	public void stateChanged(State<String, String> from, State<String, String> to) {
		if(stateContext!=null){
			log.info("{} in stateChanged Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateChanged---------------------------------------");
		}
	}

	@Override
	public void stateEntered(State<String, String> state) {
		if(stateContext!=null){
			log.info("{} in stateEntered Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateEntered---------------------------------------");
		}
	}

	@Override
	public void stateExited(State<String, String> state) {
		if(stateContext!=null){
			log.info("{} in stateExited Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateExited---------------------------------------");
		}
	}

	@Override
	public void eventNotAccepted(Message<String> event) {
		if(stateContext!=null){
			log.info("{} in eventNotAccepted Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------eventNotAccepted---------------------------------------");
		}
	}

	@Override
	public void transition(Transition<String, String> transition) {
		if(stateContext!=null){
			log.info("{} in transition Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------transition---------------------------------------");
		}
	}

	@Override
	public void transitionStarted(Transition<String, String> transition) {
		if(stateContext!=null){
			log.info("{} in transitionStarted Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------transitionStarted---------------------------------------");
		}
	}

	@Override
	public void transitionEnded(Transition<String, String> transition) {
		if(stateContext!=null){
			log.info("{} in transitionEnded Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------transitionEnded---------------------------------------");
		}
	}

	@Override
	public void stateMachineStarted(StateMachine<String, String> stateMachine) {
		if(stateContext!=null){
			log.info("{} in stateMachineStarted Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateMachineStarted---------------------------------------");
		}
	}

	@Override
	public void stateMachineStopped(StateMachine<String, String> stateMachine) {
		if(stateContext!=null){
			log.info("{} in stateMachineStopped Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateMachineStopped---------------------------------------");
		}
	}

	@Override
	public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
		if(stateContext!=null){
			log.info("{} in stateMachineError Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateMachineError---------------------------------------");
		}
	}

	@Override
	public void extendedStateChanged(Object key, Object value) {
		if(stateContext!=null){
			log.info("{} in extendedStateChanged Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------extendedStateChanged---------------------------------------");
		}
	}

	@Override
	public void stateContext(StateContext<String, String> stateContext) {
		if(stateContext!=null){
			log.info("{} in stateContext Listener",stateContext.getStateMachine().getId());
		}else{
			log.info("---------------------stateContext---------------------------------------");
		}
	}

}