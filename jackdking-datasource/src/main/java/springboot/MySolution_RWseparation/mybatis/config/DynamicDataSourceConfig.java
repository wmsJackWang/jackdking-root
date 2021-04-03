package springboot.MySolution_RWseparation.mybatis.config;

import org.springframework.context.annotation.Configuration;

 
/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:41:12
 * @todo TODO
 * @descript null
 */
@Configuration
public class DynamicDataSourceConfig {

//    @Bean(name = "masterDataSource")
//    @ConfigurationProperties(prefix = "datasource.master")
//    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "datasource.slave")
//    public DataSource slaveDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "dynamicDataSource")
//    @Primary
//    public DataSource dynamicDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource,
//                                               @Qualifier(value = "slaveDataSource") DataSource slaveDataSource){
//
//    	JDKingDynamicDataSource dynamicDataSource = new JDKingDynamicDataSource();
//        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
//        Map<Object,Object> dataSourceMap = new HashMap<>();
//        dataSourceMap.put(DataSourceType.MASTER.toString(),masterDataSource);
//        dataSourceMap.put(DataSourceType.SLAVE.toString(),slaveDataSource);
//        dynamicDataSource.setTargetDataSources(dataSourceMap);
//        return dynamicDataSource;
//    }
}