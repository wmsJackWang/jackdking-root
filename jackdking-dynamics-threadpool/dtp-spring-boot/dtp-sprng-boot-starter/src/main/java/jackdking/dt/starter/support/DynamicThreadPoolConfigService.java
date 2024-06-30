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

package jackdking.dt.starter.support;


import jackdking.dt.common.executor.support.BlockingQueueTypeEnum;
import jackdking.dt.common.executor.support.RejectedPolicyTypeEnum;
import jackdking.dt.common.model.register.DynamicThreadPoolRegisterParameter;
import jackdking.dt.common.model.register.DynamicThreadPoolRegisterWrapper;
import jackdking.dt.common.toolkit.BooleanUtil;
import jackdking.dt.core.executer.DynamicThreadPoolWrapper;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;
import jackdking.dt.core.executer.support.service.AbstractDynamicThreadPoolService;
import jackdking.dt.starter.config.ExecutorProperties;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Dynamic thread-pool config service.
 */
public class DynamicThreadPoolConfigService extends AbstractDynamicThreadPoolService {

    @Override
    public ThreadPoolExecutor registerDynamicThreadPool(DynamicThreadPoolRegisterWrapper registerWrapper) {
        DynamicThreadPoolRegisterParameter registerParameter = registerWrapper.getDynamicThreadPoolRegisterParameter();
        String threadPoolId = registerParameter.getThreadPoolId();
        ThreadPoolExecutor dynamicThreadPoolExecutor = buildDynamicThreadPoolExecutor(registerParameter);
        DynamicThreadPoolWrapper dynamicThreadPoolWrapper = DynamicThreadPoolWrapper.builder()
                .threadPoolId(threadPoolId)
                .executor(dynamicThreadPoolExecutor)
                .build();
        // Register pool.
        GlobalThreadPoolManage.registerPool(threadPoolId, dynamicThreadPoolWrapper);
        ExecutorProperties executorProperties = buildExecutorProperties(registerWrapper);
        // Register properties.
        GlobalCoreThreadPoolManage.register(threadPoolId, executorProperties);
//        DynamicThreadPoolRegisterCoreNotifyParameter notifyParameter = registerWrapper.getDynamicThreadPoolRegisterCoreNotifyParameter();
//        ThreadPoolNotifyAlarm notifyAlarm = new ThreadPoolNotifyAlarm(true, registerParameter.getActiveAlarm(), registerParameter.getCapacityAlarm());
//        notifyAlarm.setReceives(notifyParameter.getReceives());
//        notifyAlarm.setInterval(notifyParameter.getInterval());
//        // Register notify.
//        GlobalNotifyAlarmManage.put(threadPoolId, notifyAlarm);
        return dynamicThreadPoolExecutor;
    }

    private ExecutorProperties buildExecutorProperties(DynamicThreadPoolRegisterWrapper registerWrapper) {
        DynamicThreadPoolRegisterParameter registerParameter = registerWrapper.getDynamicThreadPoolRegisterParameter();
        ExecutorProperties executorProperties = ExecutorProperties.builder()
                .corePoolSize(registerParameter.getCorePoolSize())
                .maximumPoolSize(registerParameter.getMaximumPoolSize())
                .allowCoreThreadTimeOut(BooleanUtil.toBoolean(String.valueOf(registerParameter.getAllowCoreThreadTimeOut())))
                .keepAliveTime(registerParameter.getKeepAliveTime())
                .blockingQueue(BlockingQueueTypeEnum.getBlockingQueueNameByType(registerParameter.getBlockingQueueType().getType()))
                .capacityAlarm(registerParameter.getCapacity())
                .threadNamePrefix(registerParameter.getThreadNamePrefix())
                .rejectedHandler(RejectedPolicyTypeEnum.getRejectedNameByType(registerParameter.getRejectedPolicyType().getType()))
                .executeTimeOut(registerParameter.getExecuteTimeOut())
                .threadPoolId(registerParameter.getThreadPoolId())
                .build();
        return executorProperties;
    }
}
