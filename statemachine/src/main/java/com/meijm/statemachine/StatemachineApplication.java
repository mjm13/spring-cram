package com.meijm.statemachine;

import com.meijm.statemachine.config.Events;
import com.meijm.statemachine.config.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

@SpringBootApplication
public class StatemachineApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatemachineApplication.class, args);
    }

}