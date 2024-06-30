package com.jackdking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//这个注解使用场景： 开发者使用自己的数据源配置信息
@ServletComponentScan
public class MultiDsStarterExampleApplication {
	 public static void main( String[] args ){
        SpringApplication.run(MultiDsStarterExampleApplication.class, args);
	 }
}
