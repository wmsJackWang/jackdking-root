package com.jackdking.rw.separation.strategy;

import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;

public interface RWSeparationStrategy {

    RWSeparationStrategyTypeEnum getStrategyType();

    default boolean support(RWSeparationStrategyTypeEnum strategyTypeEnum) {
        return getStrategyType() == strategyTypeEnum;
    }

    void execute(String dataSourceName, MethodOperationType operationType, String monotonicProperty);

    default String getDefaultDsKey(RWSeparationDsProperties rwSeparationDsProperties) {
        String masterDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), rwSeparationDsProperties.getDefaultDs());
        return masterDataSourceKey;
    }
}
