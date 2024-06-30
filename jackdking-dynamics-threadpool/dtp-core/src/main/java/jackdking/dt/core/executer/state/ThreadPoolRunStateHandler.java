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

package jackdking.dt.core.executer.state;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.RuntimeInfo;
import jackdking.dt.common.model.ManyThreadPoolRunStateInfo;
import jackdking.dt.common.model.ThreadPoolRunStateInfo;
import jackdking.dt.common.toolkit.ByteConvertUtil;
import jackdking.dt.common.toolkit.inet.InetUtils;
import jackdking.dt.core.executer.DynamicThreadPoolExecutor;
import jackdking.dt.core.executer.DynamicThreadPoolWrapper;
import jackdking.dt.core.executer.manage.GlobalThreadPoolManage;
import jackdking.dt.core.executer.support.AbstractDynamicExecutorSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * Thread pool run state service.
 */
@Slf4j
@AllArgsConstructor
public class ThreadPoolRunStateHandler extends AbstractThreadPoolRuntime {

    private final InetUtils hippo4JInetUtils;

    private final ConfigurableEnvironment environment;

    public static final String CLIENT_IDENTIFICATION_VALUE = IdUtil.simpleUUID();

    @Override
    public ThreadPoolRunStateInfo supplement(ThreadPoolRunStateInfo poolRunStateInfo) {
        RuntimeInfo runtimeInfo = new RuntimeInfo();
        String memoryProportion = StrUtil.builder(
                "已分配: ",
                ByteConvertUtil.getPrintSize(runtimeInfo.getTotalMemory()),
                " / 最大可用: ",
                ByteConvertUtil.getPrintSize(runtimeInfo.getMaxMemory())).toString();
        poolRunStateInfo.setCurrentLoad(poolRunStateInfo.getCurrentLoad() + "%");
        poolRunStateInfo.setPeakLoad(poolRunStateInfo.getPeakLoad() + "%");
        String ipAddress = hippo4JInetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        poolRunStateInfo.setHost(ipAddress);
        poolRunStateInfo.setMemoryProportion(memoryProportion);
        poolRunStateInfo.setFreeMemory(ByteConvertUtil.getPrintSize(runtimeInfo.getFreeMemory()));
        String threadPoolId = poolRunStateInfo.getTpId();
        DynamicThreadPoolWrapper executorService = GlobalThreadPoolManage.getExecutorService(threadPoolId);
        ThreadPoolExecutor pool = executorService.getExecutor();
        String rejectedName;
        if (pool instanceof AbstractDynamicExecutorSupport) {
            rejectedName = ((DynamicThreadPoolExecutor) pool).getRedundancyHandler().getClass().getSimpleName();
        } else {
            rejectedName = pool.getRejectedExecutionHandler().getClass().getSimpleName();
        }
        poolRunStateInfo.setRejectedName(rejectedName);
        ManyThreadPoolRunStateInfo manyThreadPoolRunStateInfo = BeanUtil.toBean(poolRunStateInfo, ManyThreadPoolRunStateInfo.class);
        manyThreadPoolRunStateInfo.setIdentify(CLIENT_IDENTIFICATION_VALUE);
        String active = environment.getProperty("spring.profiles.active", "UNKNOWN");
        manyThreadPoolRunStateInfo.setActive(active.toUpperCase());
        String threadPoolState = ThreadPoolStatusHandler.getThreadPoolState(pool);
        manyThreadPoolRunStateInfo.setState(threadPoolState);
        return manyThreadPoolRunStateInfo;
    }
}
