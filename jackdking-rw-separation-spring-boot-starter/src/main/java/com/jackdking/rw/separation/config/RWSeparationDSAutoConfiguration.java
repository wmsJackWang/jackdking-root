package com.jackdking.rw.separation.config;


import com.jackdking.rw.separation.annotation.RWSeparationDBType;
import com.jackdking.rw.separation.dynamicdatasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.dynamicdatasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.enums.DatabaseMSType;
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

//    private Map<String, >

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
//    @ConditionalOnMissingBean(PrintService.class)
    @Primary
    public DataSource dynamicDataSource(){

    	System.out.println("multiJdbcProperties:"+ rwSeparationDsProperties.toString());

    	List<RWSeparationDsProperties.DsConfig> masterDsConfigs = rwSeparationDsProperties.getMasterDsConfigs();
      List<RWSeparationDsProperties.DsConfig> slaverDsConfigs = rwSeparationDsProperties.getSlaverDsConfigs();

    	if (null==masterDsConfigs || masterDsConfigs.size()<=0 || isBlank(rwSeparationDsProperties.getDefaultDs())) {
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
    		String dataSourceKey = String.format("%s:%s", DatabaseMSType.MASTER.getPrefix(), dsConfig.getDsName());
    		dataSourceMap.put(dataSourceKey, ds);
    		if(dsConfig.getDsName().equals(rwSeparationDsProperties.getDefaultDs())) {
          dynamicDataSource.setDefaultTargetDataSource(ds);
        }
    	}

    	if (!CollectionUtils.isEmpty(slaverDsConfigs)) {
        for(RWSeparationDsProperties.DsConfig dsConfig : slaverDsConfigs){
          ds = DataSourceBuilder.create()
            .driverClassName(dsConfig.getDriverClassName())
            .url(dsConfig.getJdbcUrl())
            .username(dsConfig.getUsername())
            .password(dsConfig.getPassword())
            .build();
          String dataSourceKey = String.format("%s:%s", DatabaseMSType.SLAVER.getPrefix(), dsConfig.getDsName());
          dataSourceMap.put(dataSourceKey, ds);
          if(dsConfig.getDsName().equals(rwSeparationDsProperties.getDefaultDs())) {
            dynamicDataSource.setDefaultTargetDataSource(ds);
          }
        }
      }

      dynamicDataSource.setTargetDataSources(dataSourceMap);
      return dynamicDataSource;
    }

    @Around("@annotation(dbType)")
    public Object changeDataSourceType(ProceedingJoinPoint joinPoint, RWSeparationDBType dbType) throws Throwable {

        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();

        String curType = dbType.value();
        if(!JDKingDynamicDataSource.isReady()) {
            log.info("多数据源组件没有配置数据源[{}]，使用默认数据源-> {}",dbType.value(), joinPoint.getSignature());
        }
        else if(!JDKingDynamicDataSource.contains(curType)){
            log.info("指定数据源[{}]不存在，使用默认数据源-> {}",dbType.value(),joinPoint.getSignature());
        }else{
            log.info("use datasource {} -> {}",dbType.value(),joinPoint.getSignature());
            DynamicDataSourceHolder.setType(dbType.value());
        }
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);

        log.info("remove datasource {} -> {}",dbType.value(),joinPoint.getSignature());
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
