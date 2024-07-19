package com.jackdking.sharding.strategy.rwseparation;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import com.jackdking.sharding.properties.ShardingProperties;

public interface RWSeparationStrategy {

    RWSeparationStrategyType getStrategyType();

    default boolean support(RWSeparationStrategyType strategyTypeEnum) {
        return getStrategyType() == strategyTypeEnum;
    }

    void execute(MethodOperationType operationType, ShardingContext shardingContext) throws Exception;

    default String getDefaultDsKey(ShardingProperties rwSeparationDsProperties) {
        return null;
    }
}
