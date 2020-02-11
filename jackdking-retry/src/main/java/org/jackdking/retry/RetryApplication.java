package org.jackdking.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@Configuration
@EnableAutoConfiguration
@ComponentScan({"org.jackdking.core.common","org.jackdking.retry"})//这个注解是指定basepackages，所以必须
@EnableRetry
@EnableTransactionManagement
public class RetryApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RetryApplication.class, args);
    }
}
