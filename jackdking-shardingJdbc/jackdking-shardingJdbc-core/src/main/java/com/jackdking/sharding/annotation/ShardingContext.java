package com.jackdking.sharding.annotation;

import com.jackdking.sharding.config.Constants;
import com.jackdking.sharding.enums.DatabaseMSPrefixType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardingContext {

    /**
     * 数据源分组key值
     *
     * @return
     */
    String dbGroupKey() default Constants.DEFAULT_DATASOURCE_GROUP;

    /**
     * 分库分表key处理策略
     */
    DatabaseMSPrefixType shardingKeyStrategy() default DatabaseMSPrefixType.MASTER;

    /**
     * 逻辑表名。
     * <p>支持多个表join，业务要保证一个sql多个表需要在一个数据源中。
     */
    String[] logicTableName();

    /**
     * 读写分离的策略
     *
     * @return
     */
    RWSeparationStrategyType rwSeparationStrategy() default RWSeparationStrategyType.RW_SEPARATION_ONLY_MASTER;

    /**
     * 自定义读写分离策略
     * 一版用于异地多活、cdn以及其他场景等。
     */
    String selfDefineRwStrategyName() default StringUtils.EMPTY;

    String shardingProperty();

    /**
     * 单调读情况下，需要设置单调读hash字段取值el表达式
     */
    String monotonicPropertyExp() default StringUtils.EMPTY;

    /**
     * 分隔符，用于表名拼接，user_0001
     */
    String delimiter() default "_";
}
