package org.jackdking.rpcservice.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class MessageController {

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public String pongMessage() {
	 
		JSONObject returnObj = new JSONObject();
	 
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//业务执行时间是100毫秒
	 
		returnObj.put("code", "0");//返回成功
		returnObj.put("msg", "result is ok");
		return returnObj.toString();
	}
	

	@RequestMapping(value = "/halfMessage", method = RequestMethod.POST)
	public String pongHalfRightMessage() {
	 
		JSONObject returnObj = new JSONObject();
		Random r = new Random(10);;
	 
		try {
			if(r.nextInt()>6)
				TimeUnit.MILLISECONDS.sleep(100);
			else
				TimeUnit.MILLISECONDS.sleep(1000);
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//业务执行时间是100毫秒,不正常情况是1s
		
	 
		returnObj.put("code", "0");//返回成功
		returnObj.put("msg", "result is ok");
		return returnObj.toString();
	}
}
