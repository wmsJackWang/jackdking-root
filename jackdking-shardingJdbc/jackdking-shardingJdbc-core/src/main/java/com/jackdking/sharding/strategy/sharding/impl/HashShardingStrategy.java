package com.jackdking.sharding.strategy.sharding.impl;

import com.jackdking.sharding.enums.DbShardingStrategyType;
import com.jackdking.sharding.strategy.sharding.ShardingResult;
import com.jackdking.sharding.strategy.sharding.ShardingStrategy;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DefaultShardingStrategy
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 16:19
 * @Version 2.0
 **/
public class HashShardingStrategy implements ShardingStrategy {

    @Override
    public ShardingResult sharding(Object shardingValue, int dbTotalCount, int tableTotalCount) {
        if (shardingValue == null) {
            throw new IllegalArgumentException("invalid shardingColumnValue");
        }
        if (dbTotalCount <= 0) {
            throw new IllegalArgumentException("invalid dbTotalCount:" + dbTotalCount);
        }
        if (tableTotalCount <= 0) {
            throw new IllegalArgumentException("invalid tableTotalCount:" + tableTotalCount);
        }
        if (tableTotalCount < dbTotalCount) {
            throw new IllegalArgumentException("tableTotalCount[" + tableTotalCount +
                    "] can't less than dbTotalCount[" + dbTotalCount + "]");
        }
        int hash = shardingValue.toString().hashCode();
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        int tableNo = hash % tableTotalCount;
        int dbNo = tableNo / (tableTotalCount / dbTotalCount);
        //表名后缀宽度默认为4
        String tableKey = String.format("%04d", tableNo);
        String dbKey = String.format("%d", dbNo);
        return new ShardingResult(StringUtils.EMPTY, dbKey, tableKey);
    }

}
