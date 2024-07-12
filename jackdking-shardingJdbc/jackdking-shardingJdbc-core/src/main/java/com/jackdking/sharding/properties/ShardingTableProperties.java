package com.jackdking.sharding.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ShardingTableProperties
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/10 11:24
 * @Version 2.0
 **/
@Data
@ConfigurationProperties(prefix = "jackdking.dbsharding")
public class ShardingTableProperties {

    private Map<String, String> hash;

    private Map<String, String> dataSourceMap;

    private Map<String, Map<String, String>> dbGroupDataSourceMap;
    
    
}
