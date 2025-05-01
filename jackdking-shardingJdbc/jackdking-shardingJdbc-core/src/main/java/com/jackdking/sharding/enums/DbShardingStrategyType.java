package com.jackdking.sharding.enums;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DbShardingStrategyType
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 16:30
 * @Version 2.0
 **/
@AllArgsConstructor
public enum DbShardingStrategyType {
    HASH("hash"),
    DAY("day"),
    MONTH("month"),
    YEAR("year"),
    SELF("self"),
    ;

    public String code;

    private static Map<String, DbShardingStrategyType> code2ShardingType = new HashMap<>();

    static {
        for (DbShardingStrategyType type : DbShardingStrategyType.values()) {
            code2ShardingType.put(type.code, type);
        }
    }

    public static DbShardingStrategyType forValue(String code) {
        return code2ShardingType.get(code);
    }
}
