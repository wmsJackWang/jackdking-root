package com.jackdking.sharding.strategy.sharding;

import com.jackdking.sharding.enums.DbShardingStrategyType;
import com.jackdking.sharding.strategy.sharding.impl.HashShardingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DbShardingRouteService
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 16:06
 * @Version 2.0
 **/
@Slf4j
public class DbShardingStrategyFactory {

    static Map<String, Supplier<ShardingStrategy>> stringSupplierMap = new HashMap<>();
    static {
        stringSupplierMap.put(DbShardingStrategyType.HASH.code, DbShardingStrategyFactory::createHashShardingStrategy);
    }

    public static ShardingStrategy getStrategyInstance(String strategyCode) {
       return stringSupplierMap.get(strategyCode).get();
    }

    static ShardingStrategy createHashShardingStrategy() {
       return new HashShardingStrategy();
    }
}
