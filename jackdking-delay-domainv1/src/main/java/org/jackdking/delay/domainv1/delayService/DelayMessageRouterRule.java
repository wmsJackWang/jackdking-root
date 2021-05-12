package org.jackdking.delay.domainv1.delayService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 每种消息都对应一个 处理器，用户可以在同一个服务中快速集成延时功能，多个业务功能公用一个模块。
 */
@Service
public class DelayMessageRouterRule {

	private List<DelayMessageHanler> rules = new ArrayList<>();
	
	@Autowired
	private DelayQueueExcuetor excuetor;
	
	
	public void add(DelayMessageHanler handler) {
		
		rules.add(handler);
	}
	
	/*
	 *  比较是否匹配事件
	 */
	public void route(DelayMessage msg) {
		
		List<DelayMessageHanler> matchedHanlers = new ArrayList<>();
		
		for(DelayMessageHanler rule : rules)
			if(rule.match(msg))
				matchedHanlers.add(rule);
		if(matchedHanlers.size()>0)
			for(DelayMessageHanler handler : matchedHanlers) {
				if(handler == null)
					continue;
				if(!handler.isAsyn())
					handler.handle(msg);
				else
					excuetor.submit(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							handler.handle(msg);
						}
					});
			}
		
	}
	
}
