package org.jackdking.delay.domainv1.core.hook;

public interface ConnectionHook<T> {

    void hookConnectionEvent(T message);

}
