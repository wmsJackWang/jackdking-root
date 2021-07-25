package org.jackdking.delay.domainv1.delayService.handlers;

import org.jackdking.delay.domainv1.delayService.messagetype.DelayMessage;
import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.infrastructure.util.HttpHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 10421 ， 目前延迟消息处理方式有两种
 * 第一种是直连方式，消息到期后由延迟服务根据消息的回调通知url来通知业务系统。
 * 第二种是mq异步方式，每个业务线都能申请到10个延迟消息通知队列，消息到期后由延迟服务将消息发送到通知队列，而业务侧消费这个消息队列。
 *
 */
@Slf4j
public class JdkDelayMessageHandler implements DelayMessageHandler{

	@Override
	public void handle(NotifyMessage msg) {
		// TODO Auto-generated method stub
		log.info("【JdkDelayMessageHandler】消费消息：{}",msg.getMessageJson());
		String result = HttpHelper.get(msg.getNotifyUrl());
		log.info("消息通知结果：{}",result);
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
