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
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TaskDecorator test.
 */
@Slf4j
@Component
public class TaskDecoratorTest {

    public static final String PLACEHOLDER = "site";

    private final ThreadPoolExecutor taskDecoratorTestExecutor = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new SynchronousQueue<>(),
            r -> {
                Thread t = new Thread(r);
                t.setName("client.example.taskDecorator.test");
                t.setDaemon(true);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * Test dynamic thread pool passing {@link TaskDecorator}
     * If you need to run this single test, add @PostConstruct to the method.
     */
    public void taskDecoratorTest() {
        taskDecoratorTestExecutor.execute(() -> {
            MDC.put(PLACEHOLDER, "View the official website: https://www.hippo4j.cn");
            ThreadUtil.sleep(5000);
            DynamicThreadPoolWrapper poolWrapper = GlobalThreadPoolManage.getExecutorService(GlobalTestConstant.MESSAGE_PRODUCE);
            ThreadPoolExecutor threadPoolExecutor = poolWrapper.getExecutor();
            threadPoolExecutor.execute(() -> {
                log.info("Pass context via taskDecorator MDC: {}", MDC.get(PLACEHOLDER));
            });
        });
    }

    public static class ContextCopyingDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            String placeholderVal = MDC.get(PLACEHOLDER);
            // other context...
            return () -> {
                try {
                    MDC.put(PLACEHOLDER, placeholderVal);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        }
    }
}
