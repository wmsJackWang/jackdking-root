package org.jackdking.statemachine.myfactory;
import java.util.EnumSet;

import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

//这个人使用的是orderMachine对应的状态处理器
@Component
public class OrderStateMachineBuilder {

	private final static String MACHINEID = "orderSingleMachine";
	
	 /**
	  * 构建状态机
	  * 
	 * @param beanFactory
	 * @return
	 * @throws Exception
	 */
	public StateMachine<OrderStates, OrderEvents> build(BeanFactory beanFactory) throws Exception {
		 StateMachineBuilder.Builder<OrderStates, OrderEvents> builder = StateMachineBuilder.builder();
		 
		 System.out.println("构建订单状态机");
		 
		 builder.configureConfiguration()
		 		.withConfiguration()
		 		.machineId(MACHINEID)
		 		.beanFactory(beanFactory);
		 
		 builder.configureStates()
		 			.withStates()
		 			.initial(OrderStates.UNPAID)
		 			.states(EnumSet.allOf(OrderStates.class));
		 			
		 builder.configureTransitions()
					 .withExternal()
						.source(OrderStates.UNPAID).target(OrderStates.WAITING_FOR_RECEIVE)
						.event(OrderEvents.PAY).action(action())
						.and()
					.withExternal()
						.source(OrderStates.WAITING_FOR_RECEIVE).target(OrderStates.DONE)
						.event(OrderEvents.RECEIVE);
		 			
		 return builder.build();
	 }
	
	@Bean
    public Action<OrderStates, OrderEvents> action() {
        return new Action<OrderStates, OrderEvents>() {

            @Override
            public void execute(StateContext<OrderStates, OrderEvents> context) {
               System.out.println(context);
            }
        };
    }

	
}