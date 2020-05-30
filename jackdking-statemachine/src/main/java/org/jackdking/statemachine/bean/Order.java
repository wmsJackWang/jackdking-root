package org.jackdking.statemachine.bean;
/**
 * 订单实体
 *
 * @author kutome
 * @date 2018年10月12日
 * @version V1.0
 */
public class Order {
	
	/**
	 * 订单ID
	 */
	private String id;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 订单收货地址
	 */
	private String address;
	
	/**
	 * 订单手机号
	 */
	private String phoneNum;
	
	/**
	 * 订单状态
	 */
	private String state;
	
	public Order() {
		
	}
	
	public Order(String id, String userId, String address, String phoneNum, String state) {
		super();
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.phoneNum = phoneNum;
		this.state = state;
	}

	//get/set方法省略

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", address=" + address + ", phoneNum=" + phoneNum + ", state="
				+ state + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}