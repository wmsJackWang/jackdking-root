package com.jackdking.sharding.strategy.rwseparation.impl;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.datasource.DynamicDataSourceHolder;
import com.jackdking.sharding.datasource.JDKingDynamicDataSource;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import com.jackdking.sharding.properties.RwSeparationProperties;
import com.jackdking.sharding.properties.ShardingProperties;
import com.jackdking.sharding.strategy.rwseparation.RWSeparationStrategy;
import com.jackdking.sharding.strategy.rwseparation.SelfDefineStrategy;
import com.jackdking.sharding.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class RWSeparationSelfDefineStrategy implements RWSeparationStrategy {

    // 注入属性类
    private RwSeparationProperties rwSeparationProperties;

    //自定义读写分离策略容器
    private List<SelfDefineStrategy> selfDefineStrategyList;

    @Override
    public RWSeparationStrategyType getStrategyType() {
        return RWSeparationStrategyType.RW_SEPARATION_WRITE_READ_SELF_DEFINE;
    }

    @Override
    public void execute(MethodOperationType operationType, ShardingContext shardingContext) throws Exception  {

        String masterDataSourceName = shardingContext.dbGroupKey();
        String monotonicProperty = shardingContext.monotonicPropertyExp();
        List<SelfDefineStrategy> selectedSelfStrategies = Optional.ofNullable(selfDefineStrategyList).orElseGet(Collections::emptyList)
                .stream()
                .filter(selfDefineStrategy -> selfDefineStrategy.support(shardingContext.selfDefineRwStrategyName()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(selectedSelfStrategies)) {
            log.error("[RWSeparationSelfDefineStrategy] selectedSelfStrategies is empty, selfDefineRwStrategyName:{}", shardingContext.selfDefineRwStrategyName());
            throw new RuntimeException(String.format("[RWSeparationSelfDefineStrategy] selectedSelfStrategies is empty, selfDefineRwStrategyName:%s", shardingContext.selfDefineRwStrategyName()));
        }

        if (selectedSelfStrategies.size() > 1) {
            log.error("[RWSeparationSelfDefineStrategy] selectedSelfStrategies size should be one but:{}, selfDefineRwStrategyName:{}", selectedSelfStrategies.size(), shardingContext.selfDefineRwStrategyName());
            throw new RuntimeException(String.format("[RWSeparationSelfDefineStrategy] selectedSelfStrategies  size should be one but:%d, selfDefineRwStrategyName:%s", selectedSelfStrategies.size(), shardingContext.selfDefineRwStrategyName()));
        }

        selectedSelfStrategies.get(0).execute(operationType, shardingContext);
    }
}
