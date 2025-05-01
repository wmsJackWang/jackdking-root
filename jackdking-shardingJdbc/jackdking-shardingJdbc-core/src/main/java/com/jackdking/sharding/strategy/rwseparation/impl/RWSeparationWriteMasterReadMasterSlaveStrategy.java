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
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class RWSeparationWriteMasterReadMasterSlaveStrategy implements RWSeparationStrategy {

    // 注入属性类
    private ShardingProperties shardingProperties;

    @Override
    public RWSeparationStrategyType getStrategyType() {
        return RWSeparationStrategyType.RW_SEPARATION_WRITE_MASTER_READ_MASTER_SLAVE;
    }

    @Override
    public void execute(MethodOperationType operationType, ShardingContext shardingContext)  throws Exception {

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
            List<String> dsList = Lists.newArrayList();
            dsList.addAll(wrapper.getSlaveDataSourceList());
            dsList.add(masterDataSourceKey);
            finalDataSourceKey = dsList.get(new Random().nextInt(dsList.size()));
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

    private DatabaseMSPrefixType randomGetPrefixEnum() {
        return Arrays.stream(DatabaseMSPrefixType.values()).collect(Collectors.toList())
                .get(new Random().nextInt(DatabaseMSPrefixType.values().length));
    }
}
