//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jackdking.core.config;

import org.jackdking.core.serializer.DefaultStrSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource({"classpath:config/redistemplate.properties"})
public class RedisTemplateAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RedisTemplateAutoConfiguration.class);
    @Autowired
    private Environment environment;

    public RedisTemplateAutoConfiguration() {
    }

    @Bean
    @ConditionalOnProperty(prefix = "redisSwitch", name = "enabled", havingValue = "true")
    public RedisConnectionFactory redisConnectionFactory() {
        log.info("create bean redisConnectionFactory");
        JedisConnectionFactory fac = new JedisConnectionFactory();
        fac.setHostName(this.environment.getProperty("redis.hostName"));
        fac.setPort(Integer.parseInt(this.environment.getProperty("redis.port")));
        fac.setPassword(this.environment.getProperty("redis.password"));
        fac.setTimeout(Integer.parseInt(this.environment.getProperty("redis.timeout")));
        fac.getPoolConfig().setMaxIdle(Integer.parseInt(this.environment.getProperty("redis.maxIdle")));
        fac.getPoolConfig().setMaxTotal(Integer.parseInt(this.environment.getProperty("redis.maxTotal")));
        fac.getPoolConfig().setMaxWaitMillis((long)Integer.parseInt(this.environment.getProperty("redis.maxWaitMillis")));
        fac.getPoolConfig().setMinEvictableIdleTimeMillis((long)Integer.parseInt(this.environment.getProperty("redis.minEvictableIdleTimeMillis")));
        fac.getPoolConfig().setNumTestsPerEvictionRun(Integer.parseInt(this.environment.getProperty("redis.numTestsPerEvictionRun")));
        fac.getPoolConfig().setTimeBetweenEvictionRunsMillis((long)Integer.parseInt(this.environment.getProperty("redis.timeBetweenEvictionRunsMillis")));
        fac.getPoolConfig().setTestOnBorrow(Boolean.parseBoolean(this.environment.getProperty("redis.testOnBorrow")));
        fac.getPoolConfig().setTestWhileIdle(Boolean.parseBoolean(this.environment.getProperty("redis.testWhileIdle")));
        return fac;
    }

    @Bean
    @Qualifier("simpleRedisTemplate")
    @ConditionalOnProperty(prefix = "redisSwitch", name = {"enabled", "simpleRedisTemplate"}, havingValue = "true")
    public RedisTemplate<String, String> simpleRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("create bean simpleRedisTemplate");
        RedisTemplate<String, String> redis = new RedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.afterPropertiesSet();
        return redis;
    }

    @Bean
    @Qualifier("stringSerializerRedisTemplate")
    @ConditionalOnProperty(prefix = "redisSwitch", name = {"enabled", "stringSerializerRedisTemplate"}, havingValue = "true")
    public RedisTemplate<String, String> stringSerializerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("create bean stringSerializerRedisTemplate");
        RedisTemplate<String, String> redis = new RedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redis.setKeySerializer(stringRedisSerializer);
        redis.setValueSerializer(stringRedisSerializer);
        redis.setHashKeySerializer(stringRedisSerializer);
        redis.setHashValueSerializer(stringRedisSerializer);
        redis.afterPropertiesSet();
        return redis;
    }

    @Bean
    @Qualifier("objectSerializerRedisTemplate")
    @ConditionalOnProperty(prefix = "redisSwitch", name = {"enabled", "objectSerializerRedisTemplate"}, havingValue = "true")
    public RedisTemplate<String, String> objectSerializerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("create bean objectSerializerRedisTemplate");
        RedisTemplate<String, String> redis = new RedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);
        DefaultStrSerializer defaultSerializer = new DefaultStrSerializer();
        redis.setKeySerializer(defaultSerializer);
        redis.setValueSerializer(defaultSerializer);
        redis.setHashKeySerializer(defaultSerializer);
        redis.setHashValueSerializer(defaultSerializer);
        redis.afterPropertiesSet();
        return redis;
    }
}
