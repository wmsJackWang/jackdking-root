package com.jackdking.sharding.strategy.sharding;

import com.jackdking.sharding.enums.DbShardingStrategyType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;

public interface ShardingStrategy {

    /**
     * 拆分逻辑。
     *
     * @param shardingValue   对应分表属性。
     * @param dbTotalCount    数据库实例总数量。
     * @param tableTotalCount 表总数量。
     * @return 拆分结果。
     */
    ShardingResult sharding(Object shardingValue, int dbTotalCount, int tableTotalCount);
}
