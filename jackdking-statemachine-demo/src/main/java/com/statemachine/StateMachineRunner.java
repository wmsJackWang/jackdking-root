package com.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Component
public class StateMachineRunner  implements CommandLineRunner {


    @Autowired
    private StateMachine<States, Events> stateMachine;
    
    @Override
    public void run(String... args) throws Exception {
//        stateMachine.start();
//        stateMachine.sendEvent(Events.PAY);
//        stateMachine.sendEvent(Events.RECEIVE);
        

        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.GOODS_REJECTED);
        stateMachine.sendEvent(Events.REFUND);
        

    }
}
