package org.jackdking.shardjdbcyaml.bean;

import java.sql.Timestamp;


public class TransInfo{
	
	public String userid;
	public Long orderid;
	public Timestamp ordertime;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Timestamp getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}
	@Override
	public String toString() {
		return "TransInfo [userid=" + userid + ", orderid=" + orderid + ", ordertime=" + ordertime + "]";
	}

}
