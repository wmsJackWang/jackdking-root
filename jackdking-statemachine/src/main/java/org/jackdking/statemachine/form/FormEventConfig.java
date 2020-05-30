package org.jackdking.statemachine.form;

import org.jackdking.statemachine.enums.FormEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id="formMachine")
public class FormEventConfig {
private Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 当前状态BLANK_FORM
     */
    @OnTransition(target = "BLANK_FORM")
    public void create() {
        logger.info("---空白表单---");
    }
    
    /**
     * BLANK_FORM->FULL_FORM 执行的动作
     */
    @OnTransition(source = "BLANK_FORM", target = "FULL_FORM")
    public void write(Message<FormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form"));
        logger.info("---填写完表单---");
    }
    
    /**
     * FULL_FORM->CONFIRM_FORM 执行的动作
     */
    @OnTransition(source = "FULL_FORM", target = "CONFIRM_FORM")
    public void confirm(Message<FormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form"));
        logger.info("---校验表单---");
    }
    
    /**
     * CONFIRM_FORM->SUCCESS_FORM 执行的动作
     */
    @OnTransition(source = "CONFIRM_FORM", target = "SUCCESS_FORM")
    public void submit(Message<FormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form"));
        logger.info("---表单提交成功---");
    }

}
