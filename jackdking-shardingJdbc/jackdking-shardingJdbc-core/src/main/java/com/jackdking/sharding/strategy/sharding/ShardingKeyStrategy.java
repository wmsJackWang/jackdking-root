package com.jackdking.sharding.strategy.sharding;

public interface ShardingKeyStrategy {

    /**
     * 处理分片key
     */
    String calculateShardingKey(Object shardingKey);
}
