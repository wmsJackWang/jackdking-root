package starter.limiter.samples;

import org.jackdking.limiter.anoation.AccessLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountLimiterController {

    @GetMapping("/limit/aop/user")
    @AccessLimiter(limit = 1, timeout = 1)
    public String limitAopUser(String userId) {
        return "success";
    }


    @GetMapping("/limit/aop/user3")
    @AccessLimiter(limit = 10, timeout = 1)
    public String limitAopUse3(String userId) {
        return "success";
    }

    @GetMapping("/limit/aop/user2")
    public String limitAopUser2(String userId) {
        return "success";
    }
}
