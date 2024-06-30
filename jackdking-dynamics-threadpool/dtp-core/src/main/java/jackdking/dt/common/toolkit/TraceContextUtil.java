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

package jackdking.dt.common.toolkit;

import jackdking.dt.common.constant.Constants;
import org.slf4j.MDC;


/**
 * Trace context util.
 */
public class TraceContextUtil {

    /**
     * Execute timeout trace key.
     */
    private static String EXECUTE_TIMEOUT_TRACE_KEY = Constants.EXECUTE_TIMEOUT_TRACE;

    /**
     * Get and remove.
     *
     * @return
     */
    public static String getAndRemove() {
        String val = MDC.get(EXECUTE_TIMEOUT_TRACE_KEY);
        MDC.remove(EXECUTE_TIMEOUT_TRACE_KEY);
        return val;
    }

    /**
     * Set execute timeout trace key.
     *
     * @param key
     */
    public static void setExecuteTimeoutTraceKey(String key) {
        EXECUTE_TIMEOUT_TRACE_KEY = key;
    }
}
