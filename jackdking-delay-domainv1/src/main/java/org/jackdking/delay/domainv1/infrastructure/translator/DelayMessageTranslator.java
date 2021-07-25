package org.jackdking.delay.domainv1.infrastructure.translator;

import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage;
import org.jackdking.delay.domainv1.vo.NotifyVo;

public class DelayMessageTranslator<T> {
	
	public NotifyMessage<T> toUser(NotifyVo notifyVo){
		
		long expireTime = notifyVo.getExpireTime();
		expireTime = System.currentTimeMillis()+expireTime*1000;
		String uniqueKey = notifyVo.getUniqueKey();
		String bizType = notifyVo.getBizType();
		T playload = null ;//= notifyVo.getPlayload();
		String notifyUrl = notifyVo.getNotifyUrl();
		return new NotifyMessage.Builder<T>(expireTime, uniqueKey, bizType)
				    .playload(playload)
				    .notifyUrl(notifyUrl)
				    .build();
	}
	
	public NotifyMessage<Object> toUser(String msgJsonData){
		
		return null;
	}

}
