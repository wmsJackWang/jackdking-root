package com.jackdking.sharding.strategy.sharding;

import com.jackdking.sharding.datasource.MasterWithManySlaverWrapper;
import lombok.Data;

import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DataSourceGroup
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/14 23:42
 * @Version 2.0
 **/
@Data
public class DataSourceGroupMeta {

    String groupName;

    Map<String, MasterWithManySlaverWrapper> details;
}
