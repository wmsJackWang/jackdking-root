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

package jackdking.dt.example.handler;

import jackdking.dt.common.toolkit.StringUtil;
import jackdking.dt.core.executer.support.ExecutorContext;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import static jackdking.dt.common.constant.Constants.EXECUTE_TIMEOUT_TRACE;


/**
 * Task trace builder handler.
 */
public final class TaskTraceBuilderHandler implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        String executeTimeoutTrace = MDC.get(EXECUTE_TIMEOUT_TRACE);
        Runnable taskRun = () -> {
            if (StringUtil.isNotBlank(executeTimeoutTrace)) {
                ExecutorContext.putExecuteTimeoutTrace(executeTimeoutTrace);
            }
            runnable.run();
            // There is no need to clean up here, and it will be cleaned up after the thread task is executed.
        };
        return taskRun;
    }
}
