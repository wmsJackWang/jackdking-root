package com.jackdking.sharding.annotation;

import com.jackdking.sharding.enums.DatabaseMSPrefixType;
import com.jackdking.sharding.enums.RWSeparationStrategyTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RWSeparationDBContext {

    String dsKey() default "defaultDs";

    /**
     * 数据库是主库、从库类型
     *
     */
    DatabaseMSPrefixType databaseMSType() default DatabaseMSPrefixType.MASTER;

    RWSeparationStrategyTypeEnum rwStrategyType() default RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;

    /**
     * 单调读情况下，需要设置单调读hash字段取值el表达式
     *
     */
    String monotonicPropertyExp() default StringUtils.EMPTY;
}
