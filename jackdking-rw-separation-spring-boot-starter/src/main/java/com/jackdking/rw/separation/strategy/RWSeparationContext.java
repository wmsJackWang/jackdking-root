package com.jackdking.rw.separation.strategy;

import com.jackdking.rw.separation.config.Constants;
import com.jackdking.rw.separation.config.RWSeparationDSAutoConfiguration;
import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component(value = Constants.SEPARATION_CONTEXT_BEAN_NAME)
@Slf4j
public class RWSeparationContext {

    @Autowired
    private List<RWSeparationStrategy> strategyList;

    public void decideWriteReadDs(String dataSourceName, RWSeparationStrategyTypeEnum rwSeparationStrategyTypeEnum, MethodOperationType operationType, String monotonicProperty) {

        if (!DynamicDataSourceHolder.isDataSourceExists(dataSourceName)) {
            throw new RuntimeException(String.format("主库%s不存在", dataSourceName));
        }

        String masterDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dataSourceName);
        Optional<RWSeparationStrategy> ops = Optional.ofNullable(strategyList).orElseGet(Collections::emptyList)
                .stream()
                .filter(rwSeparationStrategy -> rwSeparationStrategy.support(rwSeparationStrategyTypeEnum))
                .findFirst();


        ops.ifPresent(rwSeparationStrategy -> rwSeparationStrategy.execute(masterDataSourceKey, operationType, monotonicProperty));
    }
}
