package org.jackdking.statemachine.enums;
public enum OrderEvents {
	PAY, // 支付
	RECEIVE, // 收货
	   /*
    退货
     */
    GOODS_REJECTED,

    /*
    退款
     */
    REFUND
}
