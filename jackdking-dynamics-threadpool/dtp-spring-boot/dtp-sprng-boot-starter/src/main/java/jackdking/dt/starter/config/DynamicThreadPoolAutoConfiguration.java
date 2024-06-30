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

package jackdking.dt.starter.config;

import jackdking.dt.config.config.CommonConfig;
import jackdking.dt.config.config.DynamicThreadPoolCoreConfiguration;
import jackdking.dt.core.enable.MarkerConfiguration;
import jackdking.dt.core.executer.ThreadPoolNotifyAlarmHandler;
import jackdking.dt.message.service.JackDKingDtpSendMessageService;
import jackdking.dt.message.service.impl.JackDKingDtpSendMessageServiceImpl;
import jackdking.dt.starter.support.DynamicThreadPoolConfigService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Dynamic thread-pool auto-configuration.
 */
@Configuration
@AllArgsConstructor
@ConditionalOnBean(MarkerConfiguration.Marker.class)
@EnableConfigurationProperties(BootstrapProperties.class)
@ConditionalOnProperty(prefix = BootstrapProperties.PREFIX, value = "enable", matchIfMissing = true, havingValue = "true")
@ImportAutoConfiguration({DynamicThreadPoolCoreConfiguration.class, CommonConfig.class})
//@ImportAutoConfiguration({HttpClientConfiguration.class, NettyClientConfiguration.class, DiscoveryConfiguration.class, MessageConfiguration.class, UtilAutoConfiguration.class})
public class DynamicThreadPoolAutoConfiguration {

    private final BootstrapProperties properties;

    private final ConfigurableEnvironment environment;

    @Bean
    public DynamicThreadPoolConfigService dynamicThreadPoolConfigService() {
        return new DynamicThreadPoolConfigService();
    }
//
//    @Bean
//    public DynamicThreadPoolBannerHandler threadPoolBannerHandler() {
//        return new DynamicThreadPoolBannerHandler(properties);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ApplicationContextHolder hippo4JApplicationContextHolder() {
//        return new ApplicationContextHolder();
//    }
//
//    @Bean
//    public ClientWorker hippo4jClientWorker(HttpAgent httpAgent,
//                                            InetUtils hippo4JInetUtils,
//                                            ServerHealthCheck serverHealthCheck) {
//        String identify = IdentifyUtil.generate(environment, hippo4JInetUtils);
//        return new ClientWorker(httpAgent, identify, serverHealthCheck);
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public DynamicThreadPoolService dynamicThreadPoolConfigService(HttpAgent httpAgent,
//                                                                   ClientWorker clientWorker,
//                                                                   ServerHealthCheck serverHealthCheck,
//                                                                   ServerNotifyConfigBuilder notifyConfigBuilder,
//                                                                   Hippo4jBaseSendMessageService hippo4jBaseSendMessageService,
//                                                                   DynamicThreadPoolSubscribeConfig dynamicThreadPoolSubscribeConfig) {
//        return new DynamicThreadPoolConfigService(httpAgent, clientWorker, properties, notifyConfigBuilder, hippo4jBaseSendMessageService, dynamicThreadPoolSubscribeConfig);
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public DynamicThreadPoolPostProcessor threadPoolBeanPostProcessor(HttpAgent httpAgent,
//                                                                      ApplicationContextHolder hippo4JApplicationContextHolder,
//                                                                      DynamicThreadPoolSubscribeConfig dynamicThreadPoolSubscribeConfig) {
//        return new DynamicThreadPoolPostProcessor(properties, httpAgent, dynamicThreadPoolSubscribeConfig);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(value = ThreadDetailState.class)
//    public ThreadDetailState baseThreadDetailStateHandler() {
//        return new BaseThreadDetailStateHandler();
//    }
//
//    @Bean
//    public WebThreadPoolRunStateController poolRunStateController(ThreadPoolRunStateHandler threadPoolRunStateHandler,
//                                                                  ThreadDetailState threadDetailState) {
//        return new WebThreadPoolRunStateController(threadPoolRunStateHandler, threadDetailState);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    @SuppressWarnings("all")
//    public MessageSender messageSender(HttpAgent httpAgent) {
//        return new HttpConnectSender(httpAgent);
//    }
//
//    @Bean
//    public ReportingEventExecutor reportingEventExecutor(BootstrapProperties properties,
//                                                         MessageSender messageSender,
//                                                         ServerHealthCheck serverHealthCheck) {
//        return new ReportingEventExecutor(properties, messageSender, serverHealthCheck);
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public ServerHealthCheck httpScheduledHealthCheck(HttpAgent httpAgent) {
//        return new HttpScheduledHealthCheck(httpAgent);
//    }
//
//    @Bean
//    public RunTimeInfoCollector runTimeInfoCollector() {
//        return new RunTimeInfoCollector(properties);
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public ThreadPoolAdapterController threadPoolAdapterController(InetUtils hippo4JInetUtils) {
//        return new ThreadPoolAdapterController(environment, hippo4JInetUtils);
//    }
//
//    @Bean
//    public ThreadPoolAdapterBeanContainer threadPoolAdapterBeanContainer() {
//        return new ThreadPoolAdapterBeanContainer();
//    }
//
//    @Bean
//    public ApplicationContentPostProcessor applicationContentPostProcessor() {
//        return new ApplicationContentPostProcessor();
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public WebThreadPoolController webThreadPoolController(WebThreadPoolHandlerChoose webThreadPoolServiceChoose) {
//        return new WebThreadPoolController(webThreadPoolServiceChoose);
//    }
//
//    @Bean
//    @SuppressWarnings("all")
//    public ThreadPoolAdapterRegister threadPoolAdapterRegister(HttpAgent httpAgent,
//                                                               InetUtils hippo4JInetUtils) {
//        return new ThreadPoolAdapterRegister(httpAgent, properties, environment, hippo4JInetUtils);
//    }
//
//    @Bean
//    public NotifyConfigBuilder serverNotifyConfigBuilder(HttpAgent httpAgent,
//                                                         BootstrapProperties properties,
//                                                         AlarmControlHandler alarmControlHandler) {
//        return new ServerNotifyConfigBuilder(httpAgent, properties, alarmControlHandler);
//    }
//
//    @Bean
//    public ThreadPoolDynamicRefresh threadPoolDynamicRefresh(ThreadPoolNotifyAlarmHandler threadPoolNotifyAlarmHandler) {
//        return new ServerThreadPoolDynamicRefresh(threadPoolNotifyAlarmHandler);
//    }
//
//    @Bean
//    public DynamicThreadPoolSubscribeConfig dynamicThreadPoolSubscribeConfig(ThreadPoolDynamicRefresh threadPoolDynamicRefresh,
//                                                                             ClientWorker clientWorker) {
//        return new DynamicThreadPoolSubscribeConfig(threadPoolDynamicRefresh, clientWorker, properties);
//    }
//

    @Bean
    public JackDKingDtpSendMessageService sendMessageService() {
        return new JackDKingDtpSendMessageServiceImpl();
    }

    @Bean
    public ThreadPoolNotifyAlarmHandler threadPoolNotifyAlarmHandler(JackDKingDtpSendMessageService sendMessageService) {
        return new ThreadPoolNotifyAlarmHandler(sendMessageService);
    }
}
