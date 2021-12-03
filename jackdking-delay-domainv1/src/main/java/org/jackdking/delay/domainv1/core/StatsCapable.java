package org.jackdking.delay.domainv1.core;


/**
 * @Description: TODO  implement this interface , it mean can get stats.
 * @MethodName:
 * @Param: 
 * @return: 
 * @Author: jackdking
 * @User: 10421
 * @Date: 2021/11/14
 **/ 
public interface StatsCapable {

    /**
     * @return the Stats for this object
     */
    StatsImpl getStats();

}
