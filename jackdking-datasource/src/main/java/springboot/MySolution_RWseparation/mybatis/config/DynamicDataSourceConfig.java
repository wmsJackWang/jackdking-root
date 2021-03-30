package springboot.MySolution_RWseparation.mybatis.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.DataSourceType;
import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.JDKingDynamicDataSource;

 
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