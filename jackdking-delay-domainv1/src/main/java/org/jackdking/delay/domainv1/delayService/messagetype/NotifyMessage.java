package org.jackdking.delay.domainv1.delayService.messagetype;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Assert;


public class NotifyMessage<T> extends DelayMessage<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int failDeliverCount = 0;
	
	private String notifyUrl;
	
	public NotifyMessage(Builder<T> builder) {
		// TODO Auto-generated constructor stub
		super(builder);
		this.failDeliverCount = builder.failDeliverCount;
		this.setBizType(builder.bizType);
		this.setNotifyUrl(builder.notifyUrl);
	}

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


	public int getFailDeliverCount() {
		return failDeliverCount;
	}

	public void setFailDeliverCount(int failDeliverCount) {
		this.failDeliverCount = failDeliverCount;
	}
	
	public String getMessageJson(){
		
		JSONObject data = new JSONObject();
		data.put("playload",JSON.toJSONString(getPlayload()));
		data.put("uniqueKey", getUniqueKey());
		data.put("expireTime", getExpireTime());
		data.put("failDeliverCount", failDeliverCount);
		
		return data.toJSONString();
	}


	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public static class Builder<T>{
		
		protected T playload;
		
		protected final long expireTime;
		
		protected final String uniqueKey;
		
		protected final String bizType;
		
		protected int failDeliverCount = 0;
		
		protected String notifyUrl;
		
		public Builder(long expireTime,String uniqueKey,String bizType) {
			this.expireTime = expireTime;
			this.uniqueKey = uniqueKey;
			this.bizType = bizType;
		}
		
		public Builder<T> playload(T playload) {
			this.playload = playload;
			return this;
		}
		
		public Builder<T> failDeliverCount(int failDeliverCount) {
			this.failDeliverCount = failDeliverCount;
			return this;
		}
		
		public Builder<T> notifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
			return this;
		}
		
		public NotifyMessage<T> build() {
			
			return new NotifyMessage<T>(this);
		}
		
	}
	
}
