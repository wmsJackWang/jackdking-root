package com.jackdking.sharding.strategy.sharding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ShardingResult
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 16:07
 * @Version 2.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShardingResult {

    /**
     * 表逻辑名
     */
    private String  logicTableName;

    /**
     * 表示数据库实例的key。
     */
    private String dbKey;

    /**
     * 表示表的key。
     */
    private String tableKey;
}
