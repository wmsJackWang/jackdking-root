package org.jackdking.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableRetry
@EnableTransactionManagement
public class RetryApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RetryApplication.class, args);
    }
}
