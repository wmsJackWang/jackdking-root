package com.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @author Batman create on 2019-05-07 15:02
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 状态机 状态初始化方法
     *
     * @param states 状态
     * @throws Exception 异常类型
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates().initial(States.UNPAID).states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                // 支付(PAY)操作 UNPAID -> WAITING_FOR_RECEIVE)
                .source(States.UNPAID)
                .target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)
                // 收货(RECEIVE)操作 WAITING_FOR_RECEIVE -> DONE
                .and().withExternal()
                .source(States.WAITING_FOR_RECEIVE)
                .target(States.DONE)
                .event(Events.RECEIVE)
        		// 退货(GOODS_REJECTED) WAITING_FOR_RECEIVE-> WAITING_FOR_GOODSBACK
        		.and().withExternal()
        		.source(States.WAITING_FOR_RECEIVE).target(States.WAITING_FOR_GOODSBACK)
        		.event(Events.GOODS_REJECTED)
        		// 退款(REFUND)WAITING_FOR_GOODSBACK-> DONE
        		.and().withExternal()
        		.source(States.WAITING_FOR_GOODSBACK).target(States.DONE)
        		.event(Events.REFUND);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration().listener(listener());
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {

            @Override
            public void transition(Transition<States, Events> transition) {
                if(transition.getTarget().getId() == States.UNPAID) {
                    logger.info("订单创建，待支付");
                    return;
                }

                if(transition.getSource().getId() == States.UNPAID
                        && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
                    logger.info("用户完成支付，待收货");
                    return;
                }

                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == States.DONE) {
                    logger.info("用户已收货，订单完成");
                }

                
                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == States.WAITING_FOR_GOODSBACK) {
                    logger.info("用户已退货，待商家收货");
                }
                
                if(transition.getSource().getId() == States.WAITING_FOR_GOODSBACK
                        && transition.getTarget().getId() == States.DONE) {
                    logger.info("商家已退款，订单完成");
                }
                
                if(transition.getTarget().getId() == States.DONE) {
                    logger.info("订单结束");
                }
            }

        };
    }


}
