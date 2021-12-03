package org.jackdking.core.anoation;


import org.jackdking.core.config.RedisTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RedisTemplateAutoConfiguration.class)
@Documented
public @interface EnableRedisTemplate {
}
