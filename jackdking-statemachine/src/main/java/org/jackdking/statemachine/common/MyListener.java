package org.jackdking.statemachine.common;

import org.jackdking.statemachine.enums.OrderEvents;
import org.jackdking.statemachine.enums.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

public interface MyListener {
	
	
	static final Logger logger = LoggerFactory.getLogger(MyListener.class);


	default  StateMachineListener<OrderStates, OrderEvents> listener() {
        return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {

        	
            @Override
            public void transition(Transition<OrderStates, OrderEvents> transition) {
                if(transition.getTarget().getId() == OrderStates.UNPAID) {
                    logger.info("【StateMachineListener】订单创建，待支付");
                    return;
                }

                if(transition.getSource().getId() == OrderStates.UNPAID
                        && transition.getTarget().getId() == OrderStates.WAITING_FOR_RECEIVE) {
                    logger.info("【StateMachineListener】用户完成支付，待收货");
                    return;
                }

                if(transition.getSource().getId() == OrderStates.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == OrderStates.DONE) {
                    logger.info("【StateMachineListener】用户已收货，订单完成");
                }

                
                if(transition.getSource().getId() == OrderStates.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == OrderStates.WAITING_FOR_GOODSBACK) {
                    logger.info("【StateMachineListener】用户已退货，待商家收货");
                }
                
                if(transition.getSource().getId() == OrderStates.WAITING_FOR_GOODSBACK
                        && transition.getTarget().getId() == OrderStates.DONE) {
                    logger.info("【StateMachineListener】商家已退款，订单完成");
                }
                
                if(transition.getTarget().getId() == OrderStates.DONE) {
                    logger.info("【StateMachineListener】订单结束");
                }
            }

        };
    }
	
}
