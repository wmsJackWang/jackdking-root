package org.jackdking.statemachine.complexform;

import org.jackdking.statemachine.enums.ComplexFormEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id="complexFormMachine")
public class ComplexFormEventConfig {
private Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 当前状态BLANK_FORM
     */
    @OnTransition(target = "BLANK_FORM")
    public void create() {
        logger.info("---空白复杂表单---");
    }
    
    @OnTransition(source = "BLANK_FORM", target = "FULL_FORM")
    public void write(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form"));
        logger.info("---填写完复杂表单---");
    }
    
    @OnTransition(source = "FULL_FORM", target = "CHECK_CHOICE")
    public void check(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---校验复杂表单---");
    }
    
  //不会执行
    @OnTransition(source = "CHECK_CHOICE", target = "CONFIRM_FORM")
    public void check2confirm(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---校验表单到待提交表单(choice true)---");
    }
    
  //不会执行
    @OnTransition(source = "CHECK_CHOICE", target = "DEAL_FORM")
    public void check2deal(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---校验表单到待提交表单(choice false)---");
    }
    
    @OnTransition(source = "DEAL_FORM", target = "DEAL_CHOICE")
    public void deal(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---处理复杂表单---");
    }
    
    //不会执行
    @OnTransition(source = "DEAL_CHOICE", target = "FAILED_FORM")
    public void deal2fail(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---处理复杂表单失败(choice false)---");
    }
    
    //不会执行
    @OnTransition(source = "DEAL_CHOICE", target = "FULL_FORM")
    public void deal2full(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---处理复杂表单到重新填写表单(choice true)---");
    }
    
   
    
    /**
     * CONFIRM_FORM->SUCCESS_FORM 执行的动作
     */
    @OnTransition(source = "CONFIRM_FORM", target = "SUCCESS_FORM")
    public void submit(Message<ComplexFormEvents> message) {
    	System.out.println("传递的参数：" + message.getHeaders().get("form").toString());
        logger.info("---表单提交成功---");
    }

}
