package org.jackdking.delay.domainv1.delayService;

public class JdkDelayMessageHandler implements DelayMessageHandler{

	@Override
	public void handle(DelayMessage msg) {
		// TODO Auto-generated method stub
		System.out.println("【JdkDelayMessageHandler】消费消息："+msg.getMessageJson());
	}

	@Override
	public boolean match(DelayMessage msg) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAsyn() {
		// TODO Auto-generated method stub
		return true;
	}

}
