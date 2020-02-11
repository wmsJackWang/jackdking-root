package org.jackdking.redistemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan({"org.jackdking.core"})
public class JackdkingRedisTemplate 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(JackdkingRedisTemplate.class, args);
    }
}
