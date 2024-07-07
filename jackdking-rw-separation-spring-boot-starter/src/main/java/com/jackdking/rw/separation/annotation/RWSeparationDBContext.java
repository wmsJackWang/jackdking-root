package com.jackdking.rw.separation.annotation;

import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RWSeparationDBContext {

  String dsKey() default "defaultDs";

  /**
   *  数据库是主库、从库类型
   *
   */
  DatabaseMSPrefixType databaseMSType() default DatabaseMSPrefixType.MASTER;

  RWSeparationStrategyTypeEnum rwStrategyType() default RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;

  /**
   *  单调读情况下，需要设置单调读hash字段
   *
   */
  String monotonicProperty();
}
