package com.jackdking.sharding.properties;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
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
public class ShardingTableProperties extends HashMap<String, Object> {
    /**
     * 组名 - 库序号 - 数据源key， 数据源key为数据库key
     */
    Map<String, Map<String, String>> dbGroupDataSourceMap;

    public static void main(String[] args) {
        ShardingTableProperties tableProperties = new ShardingTableProperties();
        tableProperties.put("test", "val");
        HashMap map = new HashMap<>();
        map.put("ssss", "sssss");
        tableProperties.setDbGroupDataSourceMap(map);
        System.out.println(JSON.toJSONString(tableProperties));
    }

}
