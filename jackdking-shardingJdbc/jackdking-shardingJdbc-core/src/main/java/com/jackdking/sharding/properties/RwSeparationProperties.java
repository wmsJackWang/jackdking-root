package com.jackdking.sharding.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName RwSeparationProperties
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/15 16:59
 * @Version 2.0
 **/
@Data
@ConfigurationProperties(prefix = "jackdking.rw.separation")
public class RwSeparationProperties {
    /**读写分离策略**/
    List<String> rwSeparationStrategyList;
}
