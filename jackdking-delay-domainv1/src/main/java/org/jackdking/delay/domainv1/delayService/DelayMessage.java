package org.jackdking.delay.domainv1.delayService;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DelayMessage<T> implements Serializable{
	
	private T playload;
	
	private long expireTime;
	
	private int failDeliverCount = 0;
	
	private String uniqueKey;
	
	

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public T getPlayload() {
		return playload;
	}

	public void setPlayload(T playload) {
		this.playload = playload;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public int getFailDeliverCount() {
		return failDeliverCount;
	}

	public void setFailDeliverCount(int failDeliverCount) {
		this.failDeliverCount = failDeliverCount;
	}
	
	public String getMessageJson(){
		
		JSONObject data = new JSONObject();
		data.put("playload",JSON.toJSONString(playload));
		data.put("uniqueKey", uniqueKey);
		data.put("expireTime", expireTime);
		data.put("failDeliverCount", failDeliverCount);
		
		return data.toJSONString();
	}

}
