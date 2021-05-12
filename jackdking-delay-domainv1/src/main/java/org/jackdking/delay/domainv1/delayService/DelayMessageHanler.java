package org.jackdking.delay.domainv1.delayService;

public interface DelayMessageHanler {
	
	public void handle(DelayMessage msg);
	
	public boolean match(DelayMessage msg);
	
	public boolean isAsyn();

}
