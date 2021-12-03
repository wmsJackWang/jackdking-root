package org.jackdking.delay.domainv1.core;

import com.sun.org.glassfish.external.statistics.Statistic;

public class StatisticImpl implements Statistic, Resettable {

    protected boolean enabled;

    private String name;
    private String unit;
    private String description;
    private long startTime;
    private long lastSampleTime;
    private boolean doReset = true;

    public StatisticImpl(String name, String unit, String description) {
        this.name = name;
        this.unit = unit;
        this.description = description;
        this.startTime = System.currentTimeMillis();
        this.lastSampleTime = this.startTime;
    }

    public synchronized void reset() {
        if(isDoReset()) {
            this.startTime = System.currentTimeMillis();
            this.lastSampleTime = this.startTime;
        }
    }

    protected synchronized void updateSampleTime() {
        this.lastSampleTime = System.currentTimeMillis();
    }

    public synchronized String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append("{");
        appendFieldDescription(buffer);
        buffer.append(" }");
        return buffer.toString();
    }

    public String getName() {
        return this.name;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getDescription() {
        return this.description;
    }

    public synchronized long getStartTime() {
        return this.startTime;
    }

    public synchronized long getLastSampleTime() {
        return this.lastSampleTime;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the doReset
     */
    public boolean isDoReset() {
        return this.doReset;
    }

    /**
     * @param doReset the doReset to set
     */
    public void setDoReset(boolean doReset) {
        this.doReset = doReset;
    }


    protected synchronized void appendFieldDescription(StringBuffer buffer) {
        buffer.append(" unit: ");
        buffer.append(this.unit);
        buffer.append(" startTime: ");
        // buffer.append(new Date(startTime));
        buffer.append(this.startTime);
        buffer.append(" lastSampleTime: ");
        // buffer.append(new Date(lastSampleTime));
        buffer.append(this.lastSampleTime);
        buffer.append(" description: ");
        buffer.append(this.description);
    }
}
