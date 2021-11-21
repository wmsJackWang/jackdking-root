package org.jackdking.delay.domainv1.core.hook;

public interface MessageEventHook<T> {

    public void disconnect(T message) ;

    public T callBackMessage(T message) ;
}
