package com.jackdking.rw.separation.strategy;

import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;

public interface RWSeparationStrategy {

    RWSeparationStrategyTypeEnum getStrategyType();

    default boolean support(RWSeparationStrategyTypeEnum strategyTypeEnum) {
        return getStrategyType() == strategyTypeEnum;
    }

    void execute(String dataSourceName, MethodOperationType operationType);
}
