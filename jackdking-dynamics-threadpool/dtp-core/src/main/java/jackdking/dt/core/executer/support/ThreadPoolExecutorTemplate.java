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


import cn.hutool.core.util.ArrayUtil;

import java.util.concurrent.*;

/**
 * ThreadPool executor template.
 */
public class ThreadPoolExecutorTemplate extends ThreadPoolExecutor {

    public ThreadPoolExecutorTemplate(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue,
                                      ThreadFactory threadFactory,
                                      RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private Exception clientTrace() {
        return new Exception("Tread task root stack trace.");
    }

    @Override
    public void execute(final Runnable command) {
        super.execute(wrap(command, clientTrace()));
    }

    @Override
    public Future<?> submit(final Runnable task) {
        return super.submit(wrap(task, clientTrace()));
    }

    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return super.submit(wrap(task, clientTrace()));
    }

    private Runnable wrap(final Runnable task, final Exception clientStack) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                e.setStackTrace(ArrayUtil.addAll(clientStack.getStackTrace(), e.getStackTrace()));
                throw e;
            }
        };
    }

    private <T> Callable<T> wrap(final Callable<T> task, final Exception clientStack) {
        return () -> {
            try {
                return task.call();
            } catch (Exception e) {
                e.setStackTrace(ArrayUtil.addAll(clientStack.getStackTrace(), e.getStackTrace()));
                throw e;
            }
        };
    }
}
