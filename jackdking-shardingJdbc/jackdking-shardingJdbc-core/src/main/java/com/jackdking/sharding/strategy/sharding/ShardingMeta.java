package com.jackdking.sharding.strategy.sharding;

import com.jackdking.sharding.enums.DbShardingStrategyType;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ShardingMeta
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 16:44
 * @Version 2.0
 **/
public class ShardingMeta {

    //逻辑表名
    private String logicTableName;

    //分库总数。
    private int dbTotalCount = 1;

    //分表总数。
    private int tableTotalCount = 1;

    //表级别指定分片策略类型
    private DbShardingStrategyType dbShardingStrategyType;

    //分表策略
    private ShardingStrategy shardingStrategy;

    // 表级别指定数据源组
    private String dbGroupKey;
}
