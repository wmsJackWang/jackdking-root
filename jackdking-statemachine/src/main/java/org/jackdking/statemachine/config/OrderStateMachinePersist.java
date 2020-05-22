package org.jackdking.statemachine.config;
import org.jackdking.statemachine.bean.Order;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class OrderStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents, Order> {

	@Override
	public void write(StateMachineContext<OrderStates, OrderEvents> context, Order contextObj) throws Exception {
		//这里不做任何持久化工作
	}

	@Override
	public StateMachineContext<OrderStates, OrderEvents> read(Order contextObj) throws Exception {
		StateMachineContext<OrderStates, OrderEvents> result = new DefaultStateMachineContext<OrderStates, OrderEvents>(OrderStates.valueOf(contextObj.getState()), 
				null, null, null, null, "orderMachine");
		return result;
	}
}