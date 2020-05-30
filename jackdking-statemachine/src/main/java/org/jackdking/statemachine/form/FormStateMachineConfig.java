package org.jackdking.statemachine.form;

import java.util.EnumSet;

import org.jackdking.statemachine.enums.FormEvents;
import org.jackdking.statemachine.enums.FormStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
@EnableStateMachine(name="formSingleStateMachine")
public class FormStateMachineConfig extends EnumStateMachineConfigurerAdapter<FormStates, FormEvents> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void configure(StateMachineStateConfigurer<FormStates, FormEvents> states) throws Exception {
		states.withStates()
			.initial(FormStates.BLANK_FORM)
			.states(EnumSet.allOf(FormStates.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<FormStates, FormEvents> transitions) throws Exception {
		transitions.withExternal()
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
	}
}