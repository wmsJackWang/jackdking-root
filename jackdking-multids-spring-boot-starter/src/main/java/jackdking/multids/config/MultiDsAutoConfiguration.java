package jackdking.multids.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

import jackdking.multids.annotation.DBType;
import jackdking.multids.dynamicdatasource.DynamicDataSourceHolder;
import jackdking.multids.dynamicdatasource.JDKingDynamicDataSource;
import jackdking.multids.properties.MultiJdbcProperties;
import jackdking.multids.properties.MultiJdbcProperties.DsConfig;



@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
//@ConditionalOnClass({PrintService.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(MultiJdbcProperties.class)
public class MultiDsAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MultiDsAutoConfiguration.class);

	  // 注入属性类
    @Autowired
    private MultiJdbcProperties multiJdbcProperties;

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
//    @ConditionalOnMissingBean(PrintService.class)
    @Primary
    public DataSource dynamicDataSource(){

    	System.out.println("multiJdbcProperties:"+multiJdbcProperties.toString());

    	List<DsConfig> dsConfigs = multiJdbcProperties.getDsConfigs();

    	if(null==dsConfigs||dsConfigs.size()<=0)
    		throw new RuntimeException("多数据源未配置，请按照刚放文档配置数据源");

    	DataSource ds = null;
    	Map<Object,Object> dataSourceMap = new HashMap<Object, Object>();
    	JDKingDynamicDataSource dynamicDataSource = new JDKingDynamicDataSource();
    	for(DsConfig dsConfig : dsConfigs){
    		ds = DataSourceBuilder.create()
    				.driverClassName(dsConfig.getDriverClassName())
    				.url(dsConfig.getJdbcUrl())
    				.username(dsConfig.getUsername())
    				.password(dsConfig.getPassword())
    				.build();
    		dataSourceMap.put(dsConfig.getDsName(),ds);
    		if(dsConfig.getDsName().equals(multiJdbcProperties.getDefaultDs()))
    		  dynamicDataSource.setDefaultTargetDataSource(ds);
    	}
      dynamicDataSource.setTargetDataSources(dataSourceMap);
      return dynamicDataSource;
    }



    @Around("@annotation(dbType)")
    public Object changeDataSourceType(ProceedingJoinPoint joinPoint, DBType dbType) throws Throwable {

        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();

        String curType = dbType.value();
        if(!JDKingDynamicDataSource.isReady()) {
            logger.info("多数据源组件没有配置数据源，使用默认数据源-> {}",dbType.value(),joinPoint.getSignature());
        }
        else if(!JDKingDynamicDataSource.contains(curType)){
            logger.info("指定数据源[{}]不存在，使用默认数据源-> {}",dbType.value(),joinPoint.getSignature());
        }else{
            logger.info("use datasource {} -> {}",dbType.value(),joinPoint.getSignature());
            DynamicDataSourceHolder.setType(dbType.value());
        }
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);

        logger.info("remove datasource {} -> {}",dbType.value(),joinPoint.getSignature());
        DynamicDataSourceHolder.clearType();
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }



}
