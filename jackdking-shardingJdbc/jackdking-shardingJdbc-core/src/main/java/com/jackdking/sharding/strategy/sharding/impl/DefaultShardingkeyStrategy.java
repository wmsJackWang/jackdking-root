package com.jackdking.sharding.strategy.sharding.impl;

import com.jackdking.sharding.strategy.sharding.ShardingKeyStrategy;
import com.jackdking.sharding.strategy.sharding.ShardingResult;
import com.jackdking.sharding.strategy.sharding.ShardingStrategy;


/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DefaultShardingkeyStrategy
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 17:54
 * @Version 2.0
 **/
public class DefaultShardingkeyStrategy implements ShardingKeyStrategy {

    @Override
    public String calculateShardingKey(Object shardingKey) {
        return String.valueOf(shardingKey);
    }
}
