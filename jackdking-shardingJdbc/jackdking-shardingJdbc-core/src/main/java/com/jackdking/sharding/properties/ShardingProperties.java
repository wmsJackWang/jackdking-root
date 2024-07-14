package com.jackdking.sharding.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jackdking.sharding")
public class ShardingProperties {

    /**
     * properties配置
     */
    private Map<String, String> dataSource;

    /**
     * 表分片策略、库、表大小配置
     */
    private Map<String, String> table;

    private String defaultDs;

}
