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

package jackdking.dt.config.config;

import jackdking.dt.common.config.ApplicationContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static jackdking.dt.common.constant.Constants.AVAILABLE_PROCESSORS;

/**
 * Common config.
 */
public class CommonConfig {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder jackdkingDtpApplicationContextHolder() {
        return new ApplicationContextHolder();
    }

    @Bean
    @Primary
    public ThreadPoolTaskExecutor monitorThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor monitorThreadPool = new ThreadPoolTaskExecutor();
        monitorThreadPool.setThreadNamePrefix("server.monitor.executor.");
        monitorThreadPool.setCorePoolSize(AVAILABLE_PROCESSORS);
        monitorThreadPool.setMaxPoolSize(AVAILABLE_PROCESSORS << 1);
        monitorThreadPool.setQueueCapacity(4096);
        monitorThreadPool.setAllowCoreThreadTimeOut(true);
//        monitorThreadPool.setAwaitTerminationMillis(5000);
        return monitorThreadPool;
    }
}
