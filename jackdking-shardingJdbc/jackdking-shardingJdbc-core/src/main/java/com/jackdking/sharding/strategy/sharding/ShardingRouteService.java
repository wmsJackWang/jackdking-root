package com.jackdking.sharding.strategy.sharding;

import com.jackdking.sharding.enums.DbShardingStrategyType;
import com.jackdking.sharding.strategy.sharding.impl.HashShardingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ShardingRouteService
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/14 23:16
 * @Version 2.0
 **/
@Slf4j
@Data
public class ShardingRouteService {

    List<ShardingMeta> shardingMetaList = new ArrayList<>();

    ShardingDataSourceConfig shardingDataSourceConfig = new ShardingDataSourceConfig();

    public void addShardingMeta(ShardingMeta shardingMeta) {
        shardingMetaList.add(shardingMeta);
    }

    public ShardingStrategy decideDbShardingStrategy(DbShardingStrategyType dbShardingStrategyType) {
        if (dbShardingStrategyType.code == DbShardingStrategyType.HASH.code) {
            return new HashShardingStrategy();
        } else {
            throw new RuntimeException(dbShardingStrategyType.code + " not support");
        }
    }
}
