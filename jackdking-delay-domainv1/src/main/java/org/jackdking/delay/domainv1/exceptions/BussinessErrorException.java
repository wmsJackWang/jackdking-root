package org.jackdking.delay.domainv1.exceptions;

public class BussinessErrorException extends RuntimeException{
	
	private String exceptionType;
	
	private String desc;

	public BussinessErrorException() {}
	
	public BussinessErrorException(String msg) {

		super(msg);
		this.desc = msg;
		this.exceptionType = "NOMAL";
	}
	
	public BussinessErrorException(String exceptionType , String desc , String msg) {

		super(msg);
		this.exceptionType = exceptionType;
		this.desc = desc;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
