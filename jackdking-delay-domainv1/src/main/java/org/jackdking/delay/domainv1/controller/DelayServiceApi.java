package org.jackdking.delay.domainv1.controller;

import org.jackdking.delay.domainv1.delayService.DelayServiceWorker;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.infrastructure.translator.DelayMessageTranslator;
import org.jackdking.delay.domainv1.vo.NotifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Api(tags ="延迟域")
public class DelayServiceApi extends BaseController{

	@Autowired
	private DelayServiceWorker delayService;
	
	private static DelayMessageTranslator<String> delayMessageTranslator = new DelayMessageTranslator<String>();
	
	/**
	 * @param params  参数包含了，业务类型字段bize_type , 根据biz_type字段选择不同的延迟消息服务
	 * @return
	 */
	@RequestMapping(value = "/sendMsg" , method = RequestMethod.POST , consumes="application/json")
	@ResponseBody
	@ApiOperation(value="延迟消息发送",nickname="空白")  
	public String sendDelayMsg(@RequestBody(required=false) NotifyVo<String> notifyVo) {
		log.info("delay api entry params:{}",JSON.toJSONString(notifyVo));
		NotifyMessage<String> delayMsg = delayMessageTranslator.toUser(notifyVo);
		try {
			delayService.sendDelayMsg(delayMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			return "FAIL";
		}
		
		return "SUCCESS";
	}

}
