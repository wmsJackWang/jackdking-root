package org.jackdking.delay.domainv1.delayService.messagetype;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Assert;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class NotifyMessage<T> extends DelayMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int failDeliverCount = 0;
	
	private String notifyUrl;

	public T playload;

	public long expireTime;

	public String uniqueKey;

	public String bizType;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public NotifyMessage<T> messageBody(Map<String,Object> map){
		
		Assert.assertNotNull("expireTime param cannot be null", map.get("expireTime"));
		Assert.assertNotNull("uniqueKey param cannot be null", map.get("uniqueKey"));
		Assert.assertNotNull("playload param cannot be null", map.get("playload"));
		
		this.setPlayload((T)map.get("playload"));
		this.setExpireTime(Long.valueOf(String.valueOf(map.get("expireTime"))));;
		this.setUniqueKey(String.valueOf(map.get("uniqueKey")));
		
		return this;
	}

	public String getMessageJson(){
		
		JSONObject data = new JSONObject();
		data.put("playload",JSON.toJSONString(getPlayload()));
		data.put("uniqueKey", getUniqueKey());
		data.put("expireTime", getExpireTime());
		data.put("failDeliverCount", failDeliverCount);
		
		return data.toJSONString();
	}
}
