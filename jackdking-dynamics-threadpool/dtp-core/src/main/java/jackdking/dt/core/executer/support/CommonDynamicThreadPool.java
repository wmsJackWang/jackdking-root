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


import jackdking.dt.common.executor.support.BlockingQueueTypeEnum;
import jackdking.dt.core.executer.DynamicThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

/**
 * Common dynamic thread-pool.
 */
public class CommonDynamicThreadPool {

    public static DynamicThreadPoolExecutor getInstance(String threadPoolId) {
        DynamicThreadPoolExecutor dynamicThreadPoolExecutor = (DynamicThreadPoolExecutor) ThreadPoolBuilder.builder()
                .dynamicPool()
                .threadFactory(threadPoolId)
                .poolThreadSize(2, 4)
                .keepAliveTime(60L, TimeUnit.SECONDS)
                .workQueue(BlockingQueueTypeEnum.RESIZABLE_LINKED_BLOCKING_QUEUE, 1024)
                .build();
        return dynamicThreadPoolExecutor;
    }
}
