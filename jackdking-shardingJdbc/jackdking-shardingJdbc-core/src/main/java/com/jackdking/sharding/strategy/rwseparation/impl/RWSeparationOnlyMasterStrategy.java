package com.jackdking.sharding.strategy.rwseparation.impl;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.datasource.DynamicDataSourceHolder;
import com.jackdking.sharding.datasource.JDKingDynamicDataSource;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import com.jackdking.sharding.properties.ShardingProperties;
import com.jackdking.sharding.strategy.rwseparation.RWSeparationStrategy;
import com.jackdking.sharding.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
public class RWSeparationOnlyMasterStrategy implements RWSeparationStrategy {

    // 注入属性类
    private ShardingProperties shardingProperties;

    @Override
    public RWSeparationStrategyType getStrategyType() {
        return RWSeparationStrategyType.RW_SEPARATION_ONLY_MASTER;
    }

    @Override
    public void execute(MethodOperationType operationType, ShardingContext shardingContext) throws Exception  {

        String masterDataSourceName = shardingContext.dbGroupKey();
        String monotonicProperty = shardingContext.monotonicPropertyExp();
        if (StringUtils.isBlank(masterDataSourceName)) {
            log.debug("没有指定数据源[{}]，使用默认数据源-> {}", masterDataSourceName, shardingProperties.getDefaultDataSourceGroup());
            masterDataSourceName = getDefaultDsKey(shardingProperties);
        }

        String masterDataSourceKey = DynamicDataSourceHolder.getMasterDsKey(masterDataSourceName);
        if (!JDKingDynamicDataSource.isReady()) {
            log.info("多数据源组件没有配置数据源[{}]，使用默认数据源-> {}", masterDataSourceKey, masterDataSourceKey);
        } else if (!JDKingDynamicDataSource.contains(masterDataSourceKey)) {
            log.info("指定数据源[{}]不存在，使用默认数据源-> {}", masterDataSourceKey, masterDataSourceKey);
        } else {
            log.info("use datasource {} -> {}", masterDataSourceKey, masterDataSourceKey);
            DynamicDataSourceHolder.setType(masterDataSourceKey);
        }

    }
}
