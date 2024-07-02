package com.jackdking.rw.separation.annotation;

import com.jackdking.rw.separation.enums.DatabaseMSType;

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
  DatabaseMSType databaseMSType() default DatabaseMSType.MASTER;
  boolean resetDatasourceType() default false;
}
