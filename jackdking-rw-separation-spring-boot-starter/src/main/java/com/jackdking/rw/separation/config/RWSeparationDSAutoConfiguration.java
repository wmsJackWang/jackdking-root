package com.jackdking.rw.separation.config;


import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.datasource.MasterWithManySlaverWrapper;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(RWSeparationDsProperties.class)
public class RWSeparationDSAutoConfiguration {

    // 注入属性类
    @Autowired
    private RWSeparationDsProperties rwSeparationDsProperties;

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
//    @ConditionalOnMissingBean(PrintService.class)
    @Primary
    public DataSource dynamicDataSource(){
    	List<RWSeparationDsProperties.DsConfig> masterDsConfigs = rwSeparationDsProperties.getMasterDsConfigs();
        List<RWSeparationDsProperties.DsConfig> slaverDsConfigs = rwSeparationDsProperties.getSlaverDsConfigs();

        if (null == masterDsConfigs || masterDsConfigs.size() <= 0 || isBlank(rwSeparationDsProperties.getDefaultDs())) {
            throw new RuntimeException("数据源写库或默认库未配置，请正确配置数据源");
        }

    	DataSource ds = null;
    	Map<Object,Object> dataSourceMap = new HashMap<Object, Object>();
    	JDKingDynamicDataSource dynamicDataSource = new JDKingDynamicDataSource();
    	for(RWSeparationDsProperties.DsConfig dsConfig : masterDsConfigs){
    		ds = DataSourceBuilder.create()
    				.driverClassName(dsConfig.getDriverClassName())
    				.url(dsConfig.getJdbcUrl())
    				.username(dsConfig.getUsername())
    				.password(dsConfig.getPassword())
    				.build();
    		String dataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsConfig.getDsName());
    		dataSourceMap.put(dataSourceKey, ds);
            // 配置默认库
    		if(dsConfig.getDsName().equals(rwSeparationDsProperties.getDefaultDs())) {
                dynamicDataSource.setDefaultTargetDataSource(ds);
            }

            MasterWithManySlaverWrapper wrapper = new MasterWithManySlaverWrapper();
            wrapper.setMasterDatasource(ds);
            wrapper.setStringDataSourceMap(new HashMap<>());
            DynamicDataSourceHolder.addMasterSlave(dataSourceKey, wrapper);
    	}

    	if (!CollectionUtils.isEmpty(slaverDsConfigs)) {
            for(RWSeparationDsProperties.DsConfig dsConfig : slaverDsConfigs){
              ds = DataSourceBuilder.create()
                .driverClassName(dsConfig.getDriverClassName())
                .url(dsConfig.getJdbcUrl())
                .username(dsConfig.getUsername())
                .password(dsConfig.getPassword())
                .build();
              String slaveDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.SLAVE.getPrefix(), dsConfig.getDsName());
              String masterDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsConfig.getMasterDsName());
              dataSourceMap.put(slaveDataSourceKey, ds);
              MasterWithManySlaverWrapper masterWithManySlaverWrapper = DynamicDataSourceHolder.getDsContext().get(masterDataSourceKey);
              if (Objects.isNull(masterWithManySlaverWrapper)) {
                  throw new RuntimeException(String.format("数据源写库%s未配置，请正确配置数据源", dsConfig.getMasterDsName()));
              }
              masterWithManySlaverWrapper.getStringDataSourceMap().put(slaveDataSourceKey, ds);
            }
      }

      dynamicDataSource.setTargetDataSources(dataSourceMap);
      return dynamicDataSource;
    }

    public static boolean isBlank(String str) {

        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
          return true;
        }
        return false;
    }

}
