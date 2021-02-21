package org.jackdking.hystrix.jiangji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableCircuitBreaker
public class HystrixJiangjiApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(HystrixJiangjiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  HystrixJiangjiApplication RPC测试服务，启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
