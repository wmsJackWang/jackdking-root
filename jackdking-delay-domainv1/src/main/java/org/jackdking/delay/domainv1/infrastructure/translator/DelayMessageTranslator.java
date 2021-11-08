package org.jackdking.delay.domainv1.infrastructure.translator;

import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.vo.NotifyVo;

public class DelayMessageTranslator<T> {
	
	public NotifyMessage toUser(NotifyVo notifyVo){
		
		long expireTime = notifyVo.getExpireTime();
		expireTime = System.currentTimeMillis()+expireTime*1000;
		String uniqueKey = notifyVo.getUniqueKey();
		String bizType = notifyVo.getBizType();
		Object playLoad = notifyVo.getPlayload();
		String notifyUrl = notifyVo.getNotifyUrl();
		return NotifyMessage.builder()
				.expireTime(expireTime)
				.uniqueKey(uniqueKey)
				.bizType(bizType)
				.playload(playLoad)
				.notifyUrl(notifyUrl)
				.build();
	}
	
	public NotifyMessage<Object> toUser(String msgJsonData){
		
		return null;
	}

}
