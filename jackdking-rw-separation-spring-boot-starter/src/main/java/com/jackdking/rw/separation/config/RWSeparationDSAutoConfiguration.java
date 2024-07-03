package com.jackdking.rw.separation.config;


import com.jackdking.rw.separation.annotation.RWSeparationDBType;
import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.datasource.MasterWithManySlaverWrapper;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(RWSeparationDsProperties.class)
public class RWSeparationDSAutoConfiguration {

    // 注入属性类
    @Autowired
    private RWSeparationDsProperties rwSeparationDsProperties;

    private final Map<String, MasterWithManySlaverWrapper> dsContext = new HashMap<>();

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
            dsContext.put(dataSourceKey, wrapper);
    	}

    	if (!CollectionUtils.isEmpty(slaverDsConfigs)) {
            for(RWSeparationDsProperties.DsConfig dsConfig : slaverDsConfigs){
              ds = DataSourceBuilder.create()
                .driverClassName(dsConfig.getDriverClassName())
                .url(dsConfig.getJdbcUrl())
                .username(dsConfig.getUsername())
                .password(dsConfig.getPassword())
                .build();
              String slaveDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.SLAVER.getPrefix(), dsConfig.getDsName());
              String masterDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsConfig.getMasterDsName());
              dataSourceMap.put(slaveDataSourceKey, ds);
              MasterWithManySlaverWrapper masterWithManySlaverWrapper = dsContext.get(masterDataSourceKey);
              if (Objects.isNull(masterWithManySlaverWrapper)) {
                  throw new RuntimeException(String.format("数据源写库%s未配置，请正确配置数据源", dsConfig.getMasterDsName()));
              }
              masterWithManySlaverWrapper.getStringDataSourceMap().put(slaveDataSourceKey, ds);
            }
      }

      dynamicDataSource.setTargetDataSources(dataSourceMap);
      return dynamicDataSource;
    }

    @Around("@annotation(rwSeparationDBType)")
    public Object changeDataSourceType(ProceedingJoinPoint joinPoint, RWSeparationDBType rwSeparationDBType) throws Throwable {

        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();

        String dsName = rwSeparationDBType.value();
        DatabaseMSPrefixType databaseMSType = rwSeparationDBType.databaseMSType();
        String dataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsName);
        if(!JDKingDynamicDataSource.isReady()) {
            log.info("多数据源组件没有配置数据源[{}]，使用默认数据源-> {}",rwSeparationDBType.value(), joinPoint.getSignature());
        }
        else if(!JDKingDynamicDataSource.contains(dsName)){
            log.info("指定数据源[{}]不存在，使用默认数据源-> {}",rwSeparationDBType.value(),joinPoint.getSignature());
        }else{
            log.info("use datasource {} -> {}",rwSeparationDBType.value(),joinPoint.getSignature());
            DynamicDataSourceHolder.setType(rwSeparationDBType.value());
        }
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);

        log.info("remove datasource {} -> {}",rwSeparationDBType.value(),joinPoint.getSignature());
        DynamicDataSourceHolder.clearType();
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    public static boolean isBlank(String str) {

        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
          return true;
        }
        return false;
    }

}
