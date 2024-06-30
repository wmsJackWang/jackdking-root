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


import cn.hutool.core.thread.ThreadUtil;
import jackdking.dt.core.executer.DynamicThreadPoolWrapper;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;
import jackdking.dt.example.constant.GlobalTestConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Test alarm send message.
 */
@Slf4j
@Component
public class AlarmSendMessageTest {

    /**
     * Test alarm notification.
     * If you need to run this single test, add @PostConstruct to the method.
     */
    @SuppressWarnings("all")
    public void alarmSendMessageTest() {
        ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
        scheduledThreadPool.scheduleWithFixedDelay(() -> {
            DynamicThreadPoolWrapper poolWrapper = GlobalThreadPoolManage.getExecutorService(GlobalTestConstant.MESSAGE_PRODUCE);
            ThreadPoolExecutor poolExecutor = poolWrapper.getExecutor();
            try {
                poolExecutor.execute(() -> ThreadUtil.sleep(10240124));
            } catch (Exception ex) {
                log.error("Throw reject policy.", ex.getMessage());
            }
        }, 3, 1, TimeUnit.SECONDS);
    }

}
