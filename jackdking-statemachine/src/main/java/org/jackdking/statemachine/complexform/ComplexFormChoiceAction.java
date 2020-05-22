package org.jackdking.statemachine.complexform;

import org.jackdking.statemachine.bean.Form;
import org.jackdking.statemachine.enums.ComplexFormEvents;
import org.jackdking.statemachine.enums.ComplexFormStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ComplexFormChoiceAction implements Action<ComplexFormStates, ComplexFormEvents> {

	@Override
	public void execute(StateContext<ComplexFormStates, ComplexFormEvents> context) {
		System.out.println("into ComplexFormChoiceAction");
		Form form = context.getMessage().getHeaders().get("form", Form.class);
		System.out.println(form);
		System.out.println(context.getStateMachine().getState());
	}

}
