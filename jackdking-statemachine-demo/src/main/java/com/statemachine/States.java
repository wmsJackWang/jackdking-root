package com.statemachine;

/**
 * @author Batman create on 2019-05-07 14:59
 * 状态枚举类
 */
public enum States {

    /*
    待支付
     */
    UNPAID,
    
    /*
    待收货
     */
    WAITING_FOR_RECEIVE,
    
    /*
    结束
     */
    DONE,
    
    /*
  退货中
     */
    WAITING_FOR_GOODSBACK
    
}
