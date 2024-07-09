package com.jackdking.rw.separation.strategy;

import com.jackdking.rw.separation.enums.MethodOperationType;
import lombok.Data;
import org.aopalliance.intercept.Joinpoint;

import java.lang.reflect.Method;

@Data
public class StrategyParam {

    /**
     * 主库数据源key
     */
    String masterDataSourceKey;

    /**
     * 读写类型
     */
    MethodOperationType operationType;

    /**
     * 单调读hash字段
     */
    String monotonicProperty;

    /**
     * 拦截对象
     */
    Object target;

    /**
     * 拦截对象方法
     */
    Method targetMethod;

    /**
     * 拦截对象方法参数数组
     */
    Object[] methodArgs;

}
