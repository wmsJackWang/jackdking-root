package org.jackdking.statemachine.springfactory;

import java.util.EnumSet;

import javax.xml.bind.Marshaller.Listener;

import org.jackdking.statemachine.common.MyListener;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
//@Scope("prototype")
@EnableStateMachineFactory(name="orderMachineFactory")
public class StateMachineFactoryConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> implements MyListener{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**订单状态机ID*/
    public static final String orderStateMachineId = "orderStateFactoryMachine";
    public static final String otherOrderStateFactoryMachineId = "otherOrderStateFactoryMachine";

	//初始态设置及状态集设置
	@Override
	public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
		states.withStates().initial(OrderStates.UNPAID).states(EnumSet.allOf(OrderStates.class));
	}

	
	//状态转移设置
	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
		transitions
		.withExternal()
		.source(OrderStates.UNPAID)
		.target(OrderStates.WAITING_FOR_RECEIVE)
		.event(OrderEvents.PAY).and()
				
		.withExternal()
		.source(OrderStates.WAITING_FOR_RECEIVE)
		.target(OrderStates.DONE)
		.event(OrderEvents.RECEIVE)

		// 退货(GOODS_REJECTED) WAITING_FOR_RECEIVE-> WAITING_FOR_GOODSBACK
		.and().withExternal()
		.source(OrderStates.WAITING_FOR_RECEIVE).target(OrderStates.WAITING_FOR_GOODSBACK)
		.event(OrderEvents.GOODS_REJECTED)
		// 退款(REFUND)WAITING_FOR_GOODSBACK-> DONE
		.and().withExternal()
		.source(OrderStates.WAITING_FOR_GOODSBACK).target(OrderStates.DONE)
		.event(OrderEvents.REFUND);
	}
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
		// TODO Auto-generated method stub
		config.withConfiguration().listener(listener());
	}
	
}