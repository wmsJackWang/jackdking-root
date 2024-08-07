package com.jackdking.rw.separation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "rw.separation.ds")
public class RWSeparationDsProperties {

    private List<DsConfig> masterDsConfigs;

    private List<DsConfig> slaverDsConfigs;

    private String defaultDs;

    private Boolean enable = Boolean.FALSE;

    @Data
    public static class DsConfig {

        private String dsName;

        private String masterDsName;

        private String driverClassName;

        private String jdbcUrl;

        private String username;

        private String password;
    }
}
