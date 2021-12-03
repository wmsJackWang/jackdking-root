package starter;

import org.jackdking.core.anoation.EnableRedisTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRedisTemplate//注解替代spring.factory功能
@SpringBootApplication
public class StarterTestApplication
{

    public static void main(String[] args) {

        SpringApplication.run(StarterTestApplication.class, args);
    }
}
