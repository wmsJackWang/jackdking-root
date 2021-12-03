package org.jackdking.limiter.anoation;

import org.jackdking.limiter.enums.LimiterType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiter {

    LimiterType limitType() default LimiterType.COUNT;

    // 案例： @AccessLimiter(limit="1",timeout="1",key="user:ip:limit")
    // 描述：一个用户key在timeout时间内，最多访问limit次
    // 缓存的key
    String key() default "";
    // 限制的次数
    int limit() default  1;
    // 过期时间
    int timeout() default  1;
    // 滑动窗口 滑动步长
    int step() default 1;
    // 令牌桶速率
    int rate() default 0;
    // 桶容量
    int capacity() default 1;
    // 漏桶漏出速率
    int leakRate() default 0;
}
