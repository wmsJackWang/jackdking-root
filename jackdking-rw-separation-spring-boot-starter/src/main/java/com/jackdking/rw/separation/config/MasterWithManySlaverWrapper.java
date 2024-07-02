package com.jackdking.rw.separation.config;

import lombok.Data;

import javax.sql.DataSource;

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

  private DataSource masterDatasource;


}
