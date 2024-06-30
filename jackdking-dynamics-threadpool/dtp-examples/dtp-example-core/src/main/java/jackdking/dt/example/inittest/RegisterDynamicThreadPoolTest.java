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

package jackdking.dt.example.inittest;

import jackdking.dt.common.executor.support.BlockingQueueTypeEnum;
import jackdking.dt.common.executor.support.RejectedPolicyTypeEnum;
import jackdking.dt.common.model.register.DynamicThreadPoolRegisterParameter;
import jackdking.dt.common.model.register.DynamicThreadPoolRegisterWrapper;
import jackdking.dt.common.model.register.notify.DynamicThreadPoolRegisterCoreNotifyParameter;
import jackdking.dt.common.model.register.notify.DynamicThreadPoolRegisterServerNotifyParameter;
import jackdking.dt.common.toolkit.JSONUtil;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;
import jackdking.dt.message.NotifyPlatformEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Register dynamic thread-pool test.
 */
@Slf4j
public class RegisterDynamicThreadPoolTest {

    public static ThreadPoolExecutor registerDynamicThreadPool(String threadPoolId) {
        DynamicThreadPoolRegisterParameter parameterInfo = DynamicThreadPoolRegisterParameter.builder()
                .corePoolSize(1)
                .maximumPoolSize(2)
                .blockingQueueType(BlockingQueueTypeEnum.LINKED_BLOCKING_QUEUE)
                .capacity(1024)
                // TimeUnit.SECONDS
                .keepAliveTime(1024L)
                // TimeUnit.MILLISECONDS
                .executeTimeOut(1024L)
                .rejectedPolicyType(RejectedPolicyTypeEnum.DISCARD_POLICY)
                .isAlarm(true)
                .allowCoreThreadTimeOut(false)
                .capacityAlarm(90)
                .activeAlarm(90)
                .threadPoolId(threadPoolId)
                .threadNamePrefix(threadPoolId)
                .build();
        // Core mode and server mode, you can choose one of them.
        DynamicThreadPoolRegisterCoreNotifyParameter coreNotifyParameter = DynamicThreadPoolRegisterCoreNotifyParameter.builder()
                .receives("chen.ma")
                .interval(5)
                .build();
        DynamicThreadPoolRegisterServerNotifyParameter serverNotifyParameter = DynamicThreadPoolRegisterServerNotifyParameter.builder()
                .platform(NotifyPlatformEnum.WECHAT.name())
                .accessToken("7487d0a0-20ec-40ab-b67b-ce68db406b37")
                .interval(10)
                .receives("chen.ma")
                .build();
        DynamicThreadPoolRegisterWrapper registerWrapper = DynamicThreadPoolRegisterWrapper.builder()
                .updateIfExists(true)
                .notifyUpdateIfExists(true)
                .dynamicThreadPoolRegisterParameter(parameterInfo)
                .dynamicThreadPoolRegisterCoreNotifyParameter(coreNotifyParameter)
                .dynamicThreadPoolRegisterServerNotifyParameter(serverNotifyParameter)
                .build();
        ThreadPoolExecutor dynamicThreadPool = GlobalThreadPoolManage.dynamicRegister(registerWrapper);
        log.info("Dynamic registration thread pool parameter details: {}", JSONUtil.toJSONString(dynamicThreadPool));
        return dynamicThreadPool;
    }
}
