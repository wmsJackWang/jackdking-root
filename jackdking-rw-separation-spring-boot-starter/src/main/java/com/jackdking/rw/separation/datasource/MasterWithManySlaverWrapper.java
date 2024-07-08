package com.jackdking.rw.separation.datasource;

import lombok.Data;

import javax.sql.DataSource;
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

    private String dataSourceName;

    private DataSource masterDatasource;

    private Map<String, DataSource> stringDataSourceMap;

}
