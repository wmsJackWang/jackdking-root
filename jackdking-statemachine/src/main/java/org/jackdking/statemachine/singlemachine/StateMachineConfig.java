package org.jackdking.statemachine.singlemachine;

import java.util.EnumSet;

import org.jackdking.statemachine.common.MyListener;
import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;


//@Configuration
@EnableStateMachine(name="orderSingleMachine")
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> implements MyListener{

	private Logger logger = LoggerFactory.getLogger(getClass());

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
	
	
	//一定要注意这里设置监听器，那么该监听器就只能作用于 此状态机，状态机工厂 创建的状态机无法创建
	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
		// TODO Auto-generated method stub
		config.withConfiguration().listener(listener());
	}

//    @Bean
//    public StateMachineListener<OrderStates, OrderEvents> listener() {
//        return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
//
//            @Override
//            public void transition(Transition<OrderStates, OrderEvents> transition) {
//                if(transition.getTarget().getId() == OrderStates.UNPAID) {
//                    logger.info("【StateMachineListener】订单创建，待支付");
//                    return;
//                }
//
//                if(transition.getSource().getId() == OrderStates.UNPAID
//                        && transition.getTarget().getId() == OrderStates.WAITING_FOR_RECEIVE) {
//                    logger.info("【StateMachineListener】用户完成支付，待收货");
//                    return;
//                }
//
//                if(transition.getSource().getId() == OrderStates.WAITING_FOR_RECEIVE
//                        && transition.getTarget().getId() == OrderStates.DONE) {
//                    logger.info("【StateMachineListener】用户已收货，订单完成");
//                }
//
//                
//                if(transition.getSource().getId() == OrderStates.WAITING_FOR_RECEIVE
//                        && transition.getTarget().getId() == OrderStates.WAITING_FOR_GOODSBACK) {
//                    logger.info("【StateMachineListener】用户已退货，待商家收货");
//                }
//                
//                if(transition.getSource().getId() == OrderStates.WAITING_FOR_GOODSBACK
//                        && transition.getTarget().getId() == OrderStates.DONE) {
//                    logger.info("【StateMachineListener】商家已退款，订单完成");
//                }
//                
//                if(transition.getTarget().getId() == OrderStates.DONE) {
//                    logger.info("【StateMachineListener】订单结束");
//                }
//            }
//
//        };
//    }

	
}