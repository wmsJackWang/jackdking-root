package com.jackdking.rw.separation.strategy.impl;

import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.datasource.MasterWithManySlaverWrapper;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;
import com.jackdking.rw.separation.strategy.RWSeparationStrategy;
import com.jackdking.rw.separation.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RWSeparationWriteMasterReadMasterSlaveStrategy implements RWSeparationStrategy {

    // 注入属性类
    @Autowired
    private RWSeparationDsProperties rwSeparationDsProperties;

    @Override
    public RWSeparationStrategyTypeEnum getStrategyType() {
        return RWSeparationStrategyTypeEnum.RW_SEPARATION_WRITE_MASTER_READ_MASTER_SLAVE;
    }

    @Override
    public void execute(String masterDataSourceName, MethodOperationType operationType, String monotonicProperty) {

        if (StringUtils.isBlank(masterDataSourceName)) {
            log.debug("没有指定数据源[{}]，使用默认数据源-> {}", masterDataSourceName, rwSeparationDsProperties.getDefaultDs());
            masterDataSourceName = getDefaultDsKey(rwSeparationDsProperties);
        }

        String masterDataSourceKey = DynamicDataSourceHolder.getMasterDsKey(masterDataSourceName);
        String finalDataSourceKey = null;
        if (operationType == MethodOperationType.WRITE) {
            finalDataSourceKey =masterDataSourceKey;
        }else {
            MasterWithManySlaverWrapper wrapper =  DynamicDataSourceHolder.getDsContext().get(masterDataSourceKey);
            List<String> dsList = Lists.newArrayList();
            dsList.addAll(wrapper.getStringDataSourceMap().keySet());
            dsList.add(masterDataSourceKey);
            finalDataSourceKey = dsList.get(new Random().nextInt(dsList.size()));
        }
        if(!JDKingDynamicDataSource.isReady()) {
            log.info("多数据源组件没有配置数据源[{}]，使用默认数据源-> {}", finalDataSourceKey, finalDataSourceKey);
        }
        else if(!JDKingDynamicDataSource.contains(finalDataSourceKey)){
            log.info("指定数据源[{}]不存在，使用默认数据源-> {}", finalDataSourceKey, finalDataSourceKey);
        }else{

            log.info("use datasource {} -> {}", finalDataSourceKey, finalDataSourceKey);
            DynamicDataSourceHolder.setType(finalDataSourceKey);
        }

    }

    private DatabaseMSPrefixType randomGetPrefixEnum() {
        return Arrays.stream(DatabaseMSPrefixType.values())
                .collect(Collectors.toList())
                .get(new Random()
                        .nextInt(DatabaseMSPrefixType.values().length)
                );
    }
}
