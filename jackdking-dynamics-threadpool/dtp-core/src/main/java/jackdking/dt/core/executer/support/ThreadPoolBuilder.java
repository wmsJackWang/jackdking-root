/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jackdking.dt.core.executer.support;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.lang.Assert;
import jackdking.dt.common.executor.support.BlockingQueueTypeEnum;
import org.springframework.core.task.TaskDecorator;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Thread-pool builder.
 */
public class ThreadPoolBuilder implements Builder<ThreadPoolExecutor> {

    private boolean isFastPool;

    private boolean isDynamicPool;

    private int corePoolSize = calculateCoreNum();

    private int maxPoolSize = corePoolSize + (corePoolSize >> 1);

    private long keepAliveTime = 30000L;

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    private long executeTimeOut = 10000L;

    private int capacity = 512;

    private BlockingQueueTypeEnum blockingQueueType;

    private BlockingQueue workQueue = new LinkedBlockingQueue(capacity);

    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();

    private boolean isDaemon = false;

    private String threadNamePrefix;

    private String threadPoolId;

    private TaskDecorator taskDecorator;

    private Long awaitTerminationMillis = 5000L;

    private Boolean waitForTasksToCompleteOnShutdown = true;

    private Boolean allowCoreThreadTimeOut = false;

    private Integer calculateCoreNum() {
        int cpuCoreNum = Runtime.getRuntime().availableProcessors();
        return new BigDecimal(cpuCoreNum).divide(new BigDecimal("0.2")).intValue();
    }

    public ThreadPoolBuilder isFastPool(Boolean isFastPool) {
        this.isFastPool = isFastPool;
        return this;
    }

    public ThreadPoolBuilder dynamicPool() {
        this.isDynamicPool = true;
        return this;
    }

    public ThreadPoolBuilder threadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
        return this;
    }

    public ThreadPoolBuilder threadFactory(String threadNamePrefix, Boolean isDaemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.isDaemon = isDaemon;
        return this;
    }

    public ThreadPoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ThreadPoolBuilder maxPoolNum(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public ThreadPoolBuilder singlePool() {
        int singleNum = 1;
        this.corePoolSize = singleNum;
        this.maxPoolSize = singleNum;
        return this;
    }

    public ThreadPoolBuilder singlePool(String threadNamePrefix) {
        int singleNum = 1;
        this.corePoolSize = singleNum;
        this.maxPoolSize = singleNum;
        this.threadNamePrefix = threadNamePrefix;
        return this;
    }

    public ThreadPoolBuilder poolThreadSize(int corePoolSize, int maxPoolSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public ThreadPoolBuilder timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public ThreadPoolBuilder executeTimeOut(long executeTimeOut) {
        this.executeTimeOut = executeTimeOut;
        return this;
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        return this;
    }

    public ThreadPoolBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ThreadPoolBuilder workQueue(BlockingQueueTypeEnum queueType, int capacity) {
        this.blockingQueueType = queueType;
        this.capacity = capacity;
        return this;
    }

    public ThreadPoolBuilder rejected(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        return this;
    }

    public ThreadPoolBuilder workQueue(BlockingQueueTypeEnum blockingQueueType) {
        this.blockingQueueType = blockingQueueType;
        return this;
    }

    public ThreadPoolBuilder workQueue(BlockingQueue workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    public ThreadPoolBuilder threadPoolId(String threadPoolId) {
        this.threadPoolId = threadPoolId;
        return this;
    }

    public ThreadPoolBuilder taskDecorator(TaskDecorator taskDecorator) {
        this.taskDecorator = taskDecorator;
        return this;
    }

    public ThreadPoolBuilder awaitTerminationMillis(long awaitTerminationMillis) {
        this.awaitTerminationMillis = awaitTerminationMillis;
        return this;
    }

    public ThreadPoolBuilder waitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
        return this;
    }

    public ThreadPoolBuilder dynamicSupport(boolean waitForTasksToCompleteOnShutdown, long awaitTerminationMillis) {
        this.awaitTerminationMillis = awaitTerminationMillis;
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
        return this;
    }

    public ThreadPoolBuilder allowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    @Override
    public ThreadPoolExecutor build() {
        if (isDynamicPool) {
            return buildDynamicPool(this);
        }
        return isFastPool ? buildFastPool(this) : buildPool(this);
    }

    public static ThreadPoolBuilder builder() {
        return new ThreadPoolBuilder();
    }

    private static ThreadPoolExecutor buildPool(ThreadPoolBuilder builder) {
        return AbstractBuildThreadPoolTemplate.buildPool(buildInitParam(builder));
    }

    private static ThreadPoolExecutor buildFastPool(ThreadPoolBuilder builder) {
        return AbstractBuildThreadPoolTemplate.buildFastPool(buildInitParam(builder));
    }

    private static ThreadPoolExecutor buildDynamicPool(ThreadPoolBuilder builder) {
        return AbstractBuildThreadPoolTemplate.buildDynamicPool(buildInitParam(builder));
    }

    private static AbstractBuildThreadPoolTemplate.ThreadPoolInitParam buildInitParam(ThreadPoolBuilder builder) {
        Assert.notEmpty(builder.threadNamePrefix, "The thread name prefix cannot be empty or an empty string.");
        AbstractBuildThreadPoolTemplate.ThreadPoolInitParam initParam =
                new AbstractBuildThreadPoolTemplate.ThreadPoolInitParam(builder.threadNamePrefix, builder.isDaemon);
        initParam.setCorePoolNum(builder.corePoolSize)
                .setMaxPoolNum(builder.maxPoolSize)
                .setKeepAliveTime(builder.keepAliveTime)
                .setCapacity(builder.capacity)
                .setExecuteTimeOut(builder.executeTimeOut)
                .setRejectedExecutionHandler(builder.rejectedExecutionHandler)
                .setTimeUnit(builder.timeUnit)
                .setAllowCoreThreadTimeOut(builder.allowCoreThreadTimeOut)
                .setTaskDecorator(builder.taskDecorator);
        if (builder.isDynamicPool) {
            String threadPoolId = Optional.ofNullable(builder.threadPoolId).orElse(builder.threadNamePrefix);
            initParam.setThreadPoolId(threadPoolId);
            initParam.setWaitForTasksToCompleteOnShutdown(builder.waitForTasksToCompleteOnShutdown);
            initParam.setAwaitTerminationMillis(builder.awaitTerminationMillis);
        }
        if (!builder.isFastPool) {
            if (builder.blockingQueueType != null) {
                builder.workQueue = BlockingQueueTypeEnum.createBlockingQueue(builder.blockingQueueType.getType(), builder.capacity);
            }
            initParam.setWorkQueue(builder.workQueue);
        }
        return initParam;
    }
}
