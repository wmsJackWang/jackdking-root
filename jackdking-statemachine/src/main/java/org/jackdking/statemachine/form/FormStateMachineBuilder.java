package org.jackdking.statemachine.form;
import java.util.EnumSet;

import org.jackdking.statemachine.enums.FormEvents;
import org.jackdking.statemachine.enums.FormStates;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

/**
 * 订单状态机构建器
 */
@Component
public class FormStateMachineBuilder {

	private final static String MACHINEID = "formMachine";
	
	 /**
	  * 构建状态机
	  * 
	 * @param beanFactory
	 * @return
	 * @throws Exception
	 */
	public StateMachine<FormStates, FormEvents> build(BeanFactory beanFactory) throws Exception {
		 StateMachineBuilder.Builder<FormStates, FormEvents> builder = StateMachineBuilder.builder();
		 
		 System.out.println("构建表单状态机");
		 
		 builder.configureConfiguration()
		 		.withConfiguration()
		 		.machineId(MACHINEID)
		 		.beanFactory(beanFactory);
		 
		 builder.configureStates()
		 			.withStates()
		 			.initial(FormStates.BLANK_FORM)
		 			.states(EnumSet.allOf(FormStates.class));
		 			
		 builder.configureTransitions()
					 .withExternal()
						.source(FormStates.BLANK_FORM).target(FormStates.FULL_FORM)
						.event(FormEvents.WRITE)
						.and()
					.withExternal()
						.source(FormStates.FULL_FORM).target(FormStates.CONFIRM_FORM)
						.event(FormEvents.CONFIRM)
						.and()
					.withExternal()
						.source(FormStates.CONFIRM_FORM).target(FormStates.SUCCESS_FORM)
						.event(FormEvents.SUBMIT);
		 			
		 return builder.build();
	 }
	
}