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
import jackdking.dt.common.toolkit.JSONUtil;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static jackdking.dt.common.constant.Constants.EXECUTE_TIMEOUT_TRACE;

/**
 * Run state handler test.
 */
@Slf4j
@Component
public class RunStateHandlerTest implements ApplicationRunner {

    @Resource
    private Executor messageConsumeTtlDynamicThreadPool;

    @Resource
    private ThreadPoolExecutor messageProduceDynamicThreadPool;

    private final ThreadPoolExecutor runStateHandlerTestExecutor = new ThreadPoolExecutor(
            3,
            3,
            0L,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            r -> {
                Thread t = new Thread(r);
                t.setName("client.example.runStateHandler.test");
                t.setDaemon(true);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy());

//    @PostConstruct
    @SuppressWarnings("all")
    public void runStateHandlerTest() {
        log.info("Test thread pool runtime state interface...");
        // Start the dynamic thread pool to simulate running tasks
        runTask(messageConsumeTtlDynamicThreadPool);
        runTask(messageProduceDynamicThreadPool);
        // Dynamically register thread pool
        ThreadPoolExecutor registerDynamicThreadPool = RegisterDynamicThreadPoolTest.registerDynamicThreadPool("auto-register-dynamic-thread-pool");
        runTask(registerDynamicThreadPool);

//
//        while (true) {
//            List<String> result = GlobalThreadPoolManage.listThreadPoolId();
//            System.out.println(JSONUtil.toJSONString(result));
//        }
    }

    private void runTask(Executor executor) {
        // Simulate task run
        runStateHandlerTestExecutor.execute(() -> {
            /**
             * When the execution of the thread pool task times out, the Trace flag is put into the MDC, and it is printed out when an alarm occurs.
             */
            MDC.put(EXECUTE_TIMEOUT_TRACE, "39948722194639841.251.16612352194691531");
            ThreadUtil.sleep(5000);
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try {
                    executor.execute(() -> {
                        try {
                            int maxRandom = 10;
                            int temp = 2;
                            Random random = new Random();
                            // Assignment thread pool completedTaskCount
                            if (random.nextInt(maxRandom) % temp == 0) {
                                Thread.sleep(1000);
                            } else {
                                Thread.sleep(3000);
                            }
                        } catch (InterruptedException ignored) {
                        }
                    });
                } catch (Exception ignored) {
                }
                ThreadUtil.sleep(500);
            }
        });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runStateHandlerTest();
    }
}
