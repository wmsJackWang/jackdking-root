package org.jackdking.delay.domainv1.delayService.messagetype;

import java.io.Serializable;

import org.jackdking.delay.domainv1.delayService.messagetype.NotifyMessage.Builder;

public abstract class DelayMessage<T> implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private T playload;
	
	private long expireTime;
	
	private String uniqueKey;
	
	private String bizType;
	
	public DelayMessage(Builder<T> builder) {
		// TODO Auto-generated constructor stub
		this.playload = builder.playload;
		this.expireTime = builder.expireTime;
		this.uniqueKey = builder.uniqueKey;
	}
	

	public abstract String getMessageJson();

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
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

	
}
