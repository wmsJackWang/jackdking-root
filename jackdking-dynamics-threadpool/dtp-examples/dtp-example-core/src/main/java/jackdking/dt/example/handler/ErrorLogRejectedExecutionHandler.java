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

import jackdking.dt.common.executor.support.CustomRejectedExecutionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Custom Deny Policy.
 */
public class ErrorLogRejectedExecutionHandler implements CustomRejectedExecutionHandler {

    @Override
    public Integer getType() {
        return 12;
    }

    @Override
    public RejectedExecutionHandler generateRejected() {
        return new CustomErrorLogRejectedExecutionHandler();
    }

    public static class CustomErrorLogRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            logger.error("线程池抛出拒绝策略.");
        }
    }
}
