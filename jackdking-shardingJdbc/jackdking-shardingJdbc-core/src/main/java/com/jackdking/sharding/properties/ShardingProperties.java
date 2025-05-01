package com.jackdking.sharding.properties;

import com.jackdking.sharding.config.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jackdking.sharding")
public class ShardingProperties {

    /**
     * 组名 - 库序号 - 数据源key， 数据源key为数据库key
     */
    Map<String/** 组名 **/, Map<String/** 库序号 **/, String/** datasource key **/>> dbGroupDataSourceMap;

    private Map<String/*逻辑表名*/, String/*分表元数据表达式*/> shardingMetaMap;

    private String defaultDataSourceGroup;
    /**
     * 默认数据源
     **/

    private Map<String/*数据源key*/, String/*数据源信息*/> dataSourceMap;

    private String driverClass = Constants.DEFAULT_DRIVER_CLASS;

}
