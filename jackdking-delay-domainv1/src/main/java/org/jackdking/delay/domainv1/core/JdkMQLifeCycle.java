package org.jackdking.delay.domainv1.core;

public interface JdkMQLifeCycle {

    void start();

    void init();

    void stop();

    boolean isStarted();
}
