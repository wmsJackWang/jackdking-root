package org.jackdking.delay.domainv1.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class JdkMqConsumer implements JdkMQLifeCycle{

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    @Override
    public void start() {
        isStarted.set(true);
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

        isStarted.set(false);
    }

    @Override
    public boolean isStarted() {
        return isStarted.get();
    }
}
