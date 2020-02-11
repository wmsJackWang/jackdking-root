package org.jackdking.core.config;

import org.jackdking.core.serializer.DefaultStrSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
@PropertySource("classpath:config/redistemplate.properties")
public class RedisConfig extends JCacheConfigurerSupport {
    @Autowired
    private Environment environment;
 
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory fac = new JedisConnectionFactory();
        fac.setHostName(environment.getProperty("redis.hostName"));
        fac.setPort(Integer.parseInt(environment.getProperty("redis.port")));
        fac.setPassword(environment.getProperty("redis.password"));
        fac.setTimeout(Integer.parseInt(environment.getProperty("redis.timeout")));
        fac.getPoolConfig().setMaxIdle(Integer.parseInt(environment.getProperty("redis.maxIdle")));
        fac.getPoolConfig().setMaxTotal(Integer.parseInt(environment.getProperty("redis.maxTotal")));
        fac.getPoolConfig().setMaxWaitMillis(Integer.parseInt(environment.getProperty("redis.maxWaitMillis")));
        fac.getPoolConfig().setMinEvictableIdleTimeMillis(
                Integer.parseInt(environment.getProperty("redis.minEvictableIdleTimeMillis")));
        fac.getPoolConfig()
                .setNumTestsPerEvictionRun(Integer.parseInt(environment.getProperty("redis.numTestsPerEvictionRun")));
        fac.getPoolConfig().setTimeBetweenEvictionRunsMillis(
                Integer.parseInt(environment.getProperty("redis.timeBetweenEvictionRunsMillis")));
        fac.getPoolConfig().setTestOnBorrow(Boolean.parseBoolean(environment.getProperty("redis.testOnBorrow")));
        fac.getPoolConfig().setTestWhileIdle(Boolean.parseBoolean(environment.getProperty("redis.testWhileIdle")));
        return fac;
    }
 
    @Bean
    @Qualifier("simpleRedisTemplate")
    public RedisTemplate<String, String> simpleRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.afterPropertiesSet();
        return redis;
    }
    
    @Bean
    @Qualifier("stringSerializerRedisTemplate")
    public RedisTemplate<String, String> stringSerializerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
     
        // 设置redis的String/Value的默认序列化方式
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
    public RedisTemplate<String, String> objectSerializerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
     
        
        // 设置redis的String/Value的默认序列化方式
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        DefaultStrSerializer defaultSerializer = new DefaultStrSerializer();
        redis.setKeySerializer(defaultSerializer);
        redis.setValueSerializer(defaultSerializer);
        redis.setHashKeySerializer(defaultSerializer);
        redis.setHashValueSerializer(defaultSerializer);
     
        redis.afterPropertiesSet();
        return redis;
    }
}