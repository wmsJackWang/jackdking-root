package org.jackdking.delay.domainv1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="url通知消息实体")
public class NotifyVo<T> {
	@ApiModelProperty(name = "playload",example="{'orderId':'02039029094'}", required = true, dataType = "String")
	private T playload;

	@ApiModelProperty(name = "expireTime", example = "10" , value = "10", required = true, dataType = "String")
	private long expireTime;

	@ApiModelProperty(name = "uniqueKey", example = "jackdking", required = true, dataType = "String")
	private String uniqueKey;

	@ApiModelProperty(name = "bizType", example = "notifymsg", required = true, dataType = "String")
	private String bizType;

	@ApiModelProperty(name = "notifyUrl", example = "localhost:8080/check", required = true, dataType = "String")
	private String notifyUrl;

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

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return "NotifyVo [playload=" + playload + ", expireTime=" + expireTime + ", uniqueKey=" + uniqueKey
				+ ", bizType=" + bizType + ", notifyUrl=" + notifyUrl + "]";
	}

}
