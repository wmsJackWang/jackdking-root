package com.jackdking.sharding.strategy.rwseparation.impl;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.datasource.DynamicDataSourceHolder;
import com.jackdking.sharding.datasource.JDKingDynamicDataSource;
import com.jackdking.sharding.datasource.MasterWithManySlaverWrapper;
import com.jackdking.sharding.enums.DatabaseMSPrefixType;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import com.jackdking.sharding.properties.ShardingProperties;
import com.jackdking.sharding.strategy.rwseparation.RWSeparationStrategy;
import com.jackdking.sharding.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class RWSeparationWriteMasterReadMonotonicSlaveStrategy implements RWSeparationStrategy {

    // 注入属性类
    private ShardingProperties shardingProperties;

    @Override
    public RWSeparationStrategyType getStrategyType() {
        return RWSeparationStrategyType.RW_SEPARATION_WRITE_MASTER_READ_MONOTONIC_SLAVE;
    }

    @Override
    public void execute(MethodOperationType operationType, ShardingContext shardingContext) throws Exception {

        String masterDataSourceName = shardingContext.dbGroupKey();
        String monotonicProperty = shardingContext.monotonicPropertyExp();
        if (StringUtils.isBlank(masterDataSourceName)) {
            log.debug("没有指定数据源[{}]，使用默认数据源-> {}", masterDataSourceName, shardingProperties.getDefaultDataSourceGroup());
            masterDataSourceName = getDefaultDsKey(shardingProperties);
        }

        String masterDataSourceKey = DynamicDataSourceHolder.getMasterDsKey(masterDataSourceName);
        String finalDataSourceKey = null;
        if (operationType == MethodOperationType.WRITE) {
            finalDataSourceKey = masterDataSourceKey;
        } else {
            MasterWithManySlaverWrapper wrapper = DynamicDataSourceHolder.getDsContext().get(masterDataSourceKey);
            String monotonicVal = DynamicDataSourceHolder.monotonicReadArgsHolder.get();
            if (!StringUtils.isBlank(monotonicProperty) && Objects.isNull(monotonicVal)) {
                throw new IllegalAccessException(
                        String.format("单调读指定了hash字段:%s, 但求得的单调读hash值：%s", monotonicProperty, monotonicVal));
            }
            List<String> dsList = Lists.newArrayList();
            dsList.addAll(wrapper.getSlaveDataSourceList());
            finalDataSourceKey = dsList.get(getMonotonicIndex(dsList.size(), monotonicVal));
        }
        if (!JDKingDynamicDataSource.isReady()) {
            log.info("多数据源组件没有配置数据源[{}]，使用默认数据源-> {}", finalDataSourceKey, finalDataSourceKey);
        } else if (!JDKingDynamicDataSource.contains(finalDataSourceKey)) {
            log.info("指定数据源[{}]不存在，使用默认数据源-> {}", finalDataSourceKey, finalDataSourceKey);
        } else {

            log.info("use datasource {} -> {}", finalDataSourceKey, finalDataSourceKey);
            DynamicDataSourceHolder.setType(finalDataSourceKey);
        }

    }

    private int getMonotonicIndex(int size, String monotonicVal) {
        if (Objects.nonNull(monotonicVal)) {
            return monotonicVal.hashCode() % size;
        }
        return new Random().nextInt(size);
    }

    private DatabaseMSPrefixType randomGetPrefixEnum() {
        return Arrays.stream(DatabaseMSPrefixType.values()).collect(Collectors.toList())
                .get(new Random().nextInt(DatabaseMSPrefixType.values().length));
    }
}
