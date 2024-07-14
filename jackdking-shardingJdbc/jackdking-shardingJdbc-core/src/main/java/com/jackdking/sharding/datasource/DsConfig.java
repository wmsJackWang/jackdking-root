package com.jackdking.sharding.datasource;


import lombok.Data;

@Data
public class DsConfig {

    private String dsName;

    private String driverClassName;

    private String jdbcUrl;

    private String username;

    private String password;
}