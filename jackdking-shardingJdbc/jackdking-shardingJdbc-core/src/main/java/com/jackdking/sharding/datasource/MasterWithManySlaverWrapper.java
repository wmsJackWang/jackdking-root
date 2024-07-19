package com.jackdking.sharding.datasource;

import lombok.Data;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName MasterWithManySlaverConfig
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/2 19:13
 * @Version 2.0
 **/
@Data
public class MasterWithManySlaverWrapper {

    private String masterDataSourceName;

    private List<String> slaveDataSourceList;

}
