package com.jackdking.sharding.strategy.sharding;

import lombok.Data;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ShardingDataSourceMeta
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/14 23:27
 * @Version 2.0
 **/
@Data
public class ShardingDataSourceConfig {

    /**
     * 默认数据源组
     **/
    DataSourceGroupMeta defaultDataSourceGroup;

    /**
     * 数据源组列表
     **/
    List<DataSourceGroupMeta> dbGroupDataSourceMap;
}
