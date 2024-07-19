package com.jackdking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//这个注解使用场景： 开发者使用自己的数据源配置信息
public class ShardingJdbcStarterExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShardingJdbcStarterExampleApplication.class, args);
  }
}
