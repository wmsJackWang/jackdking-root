package org.jackdking.statemachine.singlemachine;

import org.jackdking.statemachine.bean.Order;
import org.jackdking.statemachine.enums.OrderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.util.ObjectUtils;

@WithStateMachine(name="orderSingleMachine")
public class OrderSingleEventConfig {
private Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 当前状态UNPAID
     */
    @OnTransition(target = "UNPAID")
    public void create() {
        logger.info("---【WithStateMachine】订单创建，待支付---");
    }
    
    /**
     * UNPAID->WAITING_FOR_RECEIVE 执行的动作
     */
    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        logger.info("---【WithStateMachine】用户完成支付，待收货---");
    }
    
    /**
     * WAITING_FOR_RECEIVE->DONE 执行的动作
     */
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive(Message<OrderEvents> message) {
        logger.info("---【WithStateMachine】用户已收货，订单完成---");
        Object object = message.getHeaders().get("order");
        Order order = null;
        if(!ObjectUtils.isEmpty(object))
        {
        	order = (Order)object;
        	System.out.println("消息: "+order.toString());
        }else {
        	System.out.println("消息为空");
        }
    }
    
    
    @OnTransition(source ="WAITING_FOR_RECEIVE", target = "WAITING_FOR_GOODSBACK")
    public void goodsRejected() {

        logger.info("---【WithStateMachine】用户已退货，待商家收货---");
    }

    /**
     * WAITING_FOR_GOODSBACK->DONE 执行的动作退款
     */
    @OnTransition(source = "WAITING_FOR_GOODSBACK", target = "DONE")
    public void refund() {
        logger.info("---【WithStateMachine】商家已收到货并退款，订单完成---");
    }
    
}
