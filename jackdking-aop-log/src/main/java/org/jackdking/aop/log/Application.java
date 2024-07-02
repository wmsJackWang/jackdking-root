package org.jackdking.aop.log;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//这个注解使用场景： 开发者使用自己的数据源配置信息
//@ServletComponentScan
//@EnableAspectJAutoProxy
//@Configuration
public class Application
{
	public static void main( String[] args ){

	        SpringApplication.run(Application.class, args);

	}

}
