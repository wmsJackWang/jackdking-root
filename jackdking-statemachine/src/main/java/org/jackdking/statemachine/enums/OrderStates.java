package org.jackdking.statemachine.enums;

public enum OrderStates {
	UNPAID, // 待支付
	WAITING_FOR_RECEIVE, // 待收货
	DONE // 结束
	,
    
    /*
  退货中
     */
    WAITING_FOR_GOODSBACK
}