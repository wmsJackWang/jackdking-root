package org.jackdking.delay.domainv1.delayService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.jackdking.delay.domainv1.delayService.excutor.DelayQueueExcuetor;
import org.jackdking.delay.domainv1.delayService.handlers.DelayMessageHandler;
import org.jackdking.delay.domainv1.delayService.messagetype.DelayMessage;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;

/*
 * 每种消息都对应一个 处理器，用户可以在同一个服务中快速集成延时功能，多个业务功能公用一个模块。
 */
//@Service
public class DelayMessageRouterRule {

	private List<DelayMessageHandler> handlers = new ArrayList<>();
	
	private DelayQueueExcuetor excuetor;
	
	private String handlerName = "org.jackdking.delay.domainv1.delayService.handlers.JdkDelayMessageHandler";
	
	
	public DelayMessageRouterRule setExcuetor(DelayQueueExcuetor excuetor) {
		this.excuetor = excuetor;
		return this;
	}
	
	public DelayMessageRouterRule add(DelayMessageHandler handler) {
		
		handlers.add(handler);
		return this;
	}
	
	public DelayMessageRouterRule init() {
		
		try {
			Class<DelayMessageHandler> handlerClassName = (Class<DelayMessageHandler>)Class.forName(handlerName);
			
			DelayMessageHandler handler = handlerClassName.newInstance();
					
			handlers.add(handler);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return this;
		
	}
	
	/*
	 *  比较是否匹配事件
	 */
	public void route(DelayMessage msg) {
		
		List<DelayMessageHandler> matchedHanlers = new ArrayList<>();

		if(handlers.isEmpty()) {
			throw new RuntimeException("handlers require not empty , check your properties");
		}
		
		for(DelayMessageHandler handler : handlers) {
			if(handler.match(msg)) {
				matchedHanlers.add(handler);
			}
		}
		if(matchedHanlers.size()>0) {
			NotifyMessage<String> notifyMsg = (NotifyMessage<String>)msg;
			for(DelayMessageHandler handler : matchedHanlers) {
				if(handler == null) {
					continue;
				}
				if(!handler.isAsyn()) {
					handler.handle(notifyMsg);
				}
				else {
					excuetor.submit(()->{
						handler.handle(notifyMsg);
					});
				}
			}
		}
	}
}
