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

package jackdking.dt.core.executer.state;

import cn.hutool.core.date.DateUtil;
import jackdking.dt.common.model.ThreadPoolRunStateInfo;
import jackdking.dt.common.toolkit.CalculateUtil;
import jackdking.dt.core.executer.DynamicThreadPoolExecutor;
import jackdking.dt.core.executer.DynamicThreadPoolWrapper;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Abstract threadPool runtime info.
 */
public abstract class AbstractThreadPoolRuntime {

    /**
     * Supplement.
     *
     * @param threadPoolRunStateInfo
     * @return
     */
    public abstract ThreadPoolRunStateInfo supplement(ThreadPoolRunStateInfo threadPoolRunStateInfo);

    /**
     * Get pool run state.
     *
     * @param threadPoolId
     * @return
     */
    public ThreadPoolRunStateInfo getPoolRunState(String threadPoolId) {
        DynamicThreadPoolWrapper executorService = GlobalThreadPoolManage.getExecutorService(threadPoolId);
        ThreadPoolExecutor pool = executorService.getExecutor();
        return getPoolRunState(threadPoolId, pool);
    }

    /**
     * Get pool run state.
     *
     * @param threadPoolId
     * @param executor
     * @return
     */
    public ThreadPoolRunStateInfo getPoolRunState(String threadPoolId, Executor executor) {
        ThreadPoolRunStateInfo stateInfo = new ThreadPoolRunStateInfo();
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
        // 核心线程数
        int corePoolSize = pool.getCorePoolSize();
        // 最大线程数
        int maximumPoolSize = pool.getMaximumPoolSize();
        // 线程池当前线程数 (有锁)
        int poolSize = pool.getPoolSize();
        // 活跃线程数 (有锁)
        int activeCount = pool.getActiveCount();
        // 同时进入池中的最大线程数 (有锁)
        int largestPoolSize = pool.getLargestPoolSize();
        // 线程池中执行任务总数量 (有锁)
        long completedTaskCount = pool.getCompletedTaskCount();
        // 当前负载
        String currentLoad = CalculateUtil.divide(activeCount, maximumPoolSize) + "";
        // 峰值负载
        String peakLoad = CalculateUtil.divide(largestPoolSize, maximumPoolSize) + "";
        BlockingQueue<Runnable> queue = pool.getQueue();
        // 队列元素个数
        int queueSize = queue.size();
        // 队列类型
        String queueType = queue.getClass().getSimpleName();
        // 队列剩余容量
        int remainingCapacity = queue.remainingCapacity();
        // 队列容量
        int queueCapacity = queueSize + remainingCapacity;
        stateInfo.setCoreSize(corePoolSize);
        stateInfo.setTpId(threadPoolId);
        stateInfo.setPoolSize(poolSize);
        stateInfo.setMaximumSize(maximumPoolSize);
        stateInfo.setActiveSize(activeCount);
        stateInfo.setCurrentLoad(currentLoad);
        stateInfo.setPeakLoad(peakLoad);
        stateInfo.setQueueType(queueType);
        stateInfo.setQueueSize(queueSize);
        stateInfo.setQueueCapacity(queueCapacity);
        stateInfo.setQueueRemainingCapacity(remainingCapacity);
        stateInfo.setLargestPoolSize(largestPoolSize);
        stateInfo.setCompletedTaskCount(completedTaskCount);
        long rejectCount =
                pool instanceof DynamicThreadPoolExecutor ? ((DynamicThreadPoolExecutor) pool).getRejectCountNum() : -1L;
        stateInfo.setRejectCount(rejectCount);
        stateInfo.setClientLastRefreshTime(DateUtil.formatDateTime(new Date()));
        stateInfo.setTimestamp(System.currentTimeMillis());
        return supplement(stateInfo);
    }
}
