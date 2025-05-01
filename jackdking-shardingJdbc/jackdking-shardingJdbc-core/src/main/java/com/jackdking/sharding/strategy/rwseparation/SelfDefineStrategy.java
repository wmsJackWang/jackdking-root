package com.jackdking.sharding.strategy.rwseparation;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import com.jackdking.sharding.properties.ShardingProperties;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName SelfDefineStrategy
 * @Description 自定义读写分离策略接口
 * @Author jackdking
 * @Date 2024/7/15 17:14
 * @Version 2.0
 **/
public interface SelfDefineStrategy {

    String getStrategyType();

    default boolean support(String strategyType) {
        return StringUtils.equalsIgnoreCase(getStrategyType(), strategyType);
    }

    void execute(MethodOperationType operationType, ShardingContext shardingContext) throws Exception;

    default String getDefaultDsKey(ShardingProperties rwSeparationDsProperties) {
        return null;
    }
}
