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

package jackdking.dt.example.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import jackdking.dt.core.executer.DynamicThreadPool;
import jackdking.dt.core.executer.support.ThreadPoolBuilder;
import jackdking.dt.example.handler.TaskTraceBuilderHandler;
//import jackdking.dt.example.inittest.TaskDecoratorTest;
import jackdking.dt.example.inittest.TaskDecoratorTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static jackdking.dt.example.constant.GlobalTestConstant.MESSAGE_CONSUME;
import static jackdking.dt.example.constant.GlobalTestConstant.MESSAGE_PRODUCE;


/**
 * Dynamic thread-pool config.
 */
@Slf4j
@Configuration
public class DynamicThreadPoolConfig {

    @Bean
    @DynamicThreadPool
    public Executor messageConsumeTtlDynamicThreadPool() {
        String threadPoolId = MESSAGE_CONSUME;
        ThreadPoolExecutor customExecutor = ThreadPoolBuilder.builder()
                .dynamicPool()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .executeTimeOut(800L)
                .waitForTasksToCompleteOnShutdown(true)
                .awaitTerminationMillis(5000L)
                .taskDecorator(new TaskTraceBuilderHandler())
                .build();
        // Ali ttl adaptation use case.
        Executor ttlExecutor = TtlExecutors.getTtlExecutor(customExecutor);
        return ttlExecutor;
    }

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor messageProduceDynamicThreadPool() {
        String threadPoolId = MESSAGE_PRODUCE;
        ThreadPoolExecutor produceExecutor = ThreadPoolBuilder.builder()
                .dynamicPool()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .executeTimeOut(900L)
                .waitForTasksToCompleteOnShutdown(true)
                .awaitTerminationMillis(5000L)
                /**
                 * Context passing, test cases: {@link TaskDecoratorTest}
                 */
                .taskDecorator(new TaskDecoratorTest.ContextCopyingDecorator())
                .build();
        return produceExecutor;
    }
}
