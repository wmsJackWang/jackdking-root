package org.jackdking.delay.domainv1.delayService.handlers;

import org.jackdking.delay.domainv1.delayService.messagetype.DelayMessage;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;

public interface DelayMessageHandler {
	
	public void handle(NotifyMessage msg);
	
	public boolean match(DelayMessage msg);
	
	public boolean isAsyn();

}
