package org.jackdking.shardjdbcyaml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@MapperScan("org.jackdking.shardjdbcyaml.mapper")
public class ShardingJdbcYamlApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ShardingJdbcYamlApplication.class, args); 
    }
}
