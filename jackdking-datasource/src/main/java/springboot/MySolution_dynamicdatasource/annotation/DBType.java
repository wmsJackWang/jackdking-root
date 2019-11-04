package springboot.MySolution_dynamicdatasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.DataSourceType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBType {
    DataSourceType value() default DataSourceType.MASTER;
}