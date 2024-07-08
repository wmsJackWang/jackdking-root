package com.jackdking.rw.separation.strategy.impl;

import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;
import com.jackdking.rw.separation.strategy.RWSeparationStrategy;
import com.jackdking.rw.separation.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RWSeparationOnlyMasterStrategy implements RWSeparationStrategy {

    // 注入属性类
    @Autowired
    private RWSeparationDsProperties rwSeparationDsProperties;

    @Override
    public RWSeparationStrategyTypeEnum getStrategyType() {
        return RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;
    }

    @Override
    public void execute(String masterDataSourceName, MethodOperationType operationType, String monotonicProperty) {

        if (StringUtils.isBlank(masterDataSourceName)) {
            log.debug("没有指定数据源[{}]，使用默认数据源-> {}", masterDataSourceName, rwSeparationDsProperties.getDefaultDs());
            masterDataSourceName = getDefaultDsKey(rwSeparationDsProperties);
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
