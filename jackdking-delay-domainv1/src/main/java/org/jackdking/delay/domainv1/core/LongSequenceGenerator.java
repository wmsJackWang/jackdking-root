package org.jackdking.delay.domainv1.core;

public class LongSequenceGenerator {

    private long lastSequenceId;

    public synchronized long getNextSequenceId() {
        return ++lastSequenceId;
    }

    public synchronized long getLastSequenceId() {
        return lastSequenceId;
    }

    public synchronized void setLastSequenceId(long l) {
        lastSequenceId = l;
    }

}
