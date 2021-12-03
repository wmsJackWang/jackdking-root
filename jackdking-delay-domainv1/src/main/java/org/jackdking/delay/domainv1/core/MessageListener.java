package org.jackdking.delay.domainv1.core;

import org.jackdking.delay.domainv1.protocol.Packet;

public interface MessageListener {

    /**
     * @Description: TODO,监听得到server接收消息成功的ack后，执行监听器的业务逻辑。
     * @MethodName: onMessage
     * @Param: Packet
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/11/13
     **/ 
    public void onMessage(Packet ackEvent);
}
