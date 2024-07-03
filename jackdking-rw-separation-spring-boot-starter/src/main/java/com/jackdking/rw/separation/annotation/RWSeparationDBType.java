package com.jackdking.rw.separation.annotation;

import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RWSeparationDBType {

  String value() default "defaultDs";

  /**
   *  数据库是主库、从库类型
   * @return
   */
  DatabaseMSPrefixType databaseMSType() default DatabaseMSPrefixType.MASTER;

  RWSeparationStrategyTypeEnum rwStrategyType() default RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;

  boolean resetDatasourceType() default false;
}
