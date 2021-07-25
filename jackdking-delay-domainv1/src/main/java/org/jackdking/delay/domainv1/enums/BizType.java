package org.jackdking.delay.domainv1.enums;

public enum BizType{

	TOPIC_MSG("topic_message",001),
	NOTIFY_MSG("notifyUrl_message",002);
	
	public String name;
	public int code;
	
	BizType(String name , int code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCode() {
		return this.code;
	}
	
}
