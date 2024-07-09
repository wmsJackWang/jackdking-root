package com.jackdking.rw.separation.plugins;

import com.alibaba.fastjson2.JSON;
import com.jackdking.rw.separation.annotation.RWSeparationDBContext;
import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import com.jackdking.rw.separation.strategy.StrategyParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class RWSeparationExecutePlugin extends BaseInterceptor {

    /** 可以识别为 添加、更新、删除类型的 SQL 语句 */
    public static final List<SqlCommandType> UPDATE_SQL_LIST = Arrays.asList(SqlCommandType.INSERT,
            SqlCommandType.UPDATE, SqlCommandType.DELETE);

    /** SQL 语句中出现的悲观锁标识 */
    private static final String LOCK_KEYWORD = "for update";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 通过 invocation 获取 MappedStatement 与 拦截方法的形参信息
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];

        // 通过反射检查要执行的方法，如果标注了 @DataSource 则检查其 value
        String clazzStr = ms.getId().substring(0, ms.getId().lastIndexOf("."));
        String methodStr = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
        // 由于 mybatis 同一个接口方法不能重载
        Class<?> classObj = Class.forName(clazzStr);
        Method[] mapperMethods = classObj.getMethods();
        Method targetMethod = null;
        for (Method mapperMethod : mapperMethods) {
            if (mapperMethod.getName().equals(methodStr)) {
                targetMethod = mapperMethod;
                break;
            }
        }

        // 获取 sqlCommandType
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        String dataSourceName = null;
        RWSeparationStrategyTypeEnum rwSeparationStrategyTypeEnum = RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;
        MethodOperationType operationType = MethodOperationType.WRITE;
        String monotonicProperty = null;
        // 获取 SQL
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");

        if (sqlCommandType.equals(SqlCommandType.SELECT)) {
            operationType = MethodOperationType.READ;
        } else if (UPDATE_SQL_LIST.contains(sqlCommandType) || sql.contains(LOCK_KEYWORD)) {
            operationType = MethodOperationType.WRITE;
        } else {
            operationType = MethodOperationType.WRITE;
        }

        // 判断方法上是否带有自定义@RWSeparationDBType注解
        RWSeparationDBContext methodRWSeparationDBType = DynamicDataSourceHolder.separationDBContextHolder.get();
        RWSeparationDBContext classRWSeparationDBType = DynamicDataSourceHolder.separationDBContextHolder.get();
        RWSeparationDBContext finalRWSeparationDBContext = getFinalRWSeparationDBContext(methodRWSeparationDBType,
                classRWSeparationDBType);
        // 方法注解
        if (finalRWSeparationDBContext != null) {
            rwSeparationStrategyTypeEnum = finalRWSeparationDBContext.rwStrategyType();
            dataSourceName = finalRWSeparationDBContext.dsKey();
            monotonicProperty = finalRWSeparationDBContext.monotonicPropertyExp();
            StrategyParam param = new StrategyParam();
            param.setTargetMethod(targetMethod);
            param.setTarget(classObj);
            // param.setMethodArgs(targetMethod.getA);
            log.info("data:{}", JSON.toJSONString(invocation.getArgs()[1]));
            rwSeparationContext.decideWriteReadDs(dataSourceName, rwSeparationStrategyTypeEnum, operationType,
                    monotonicProperty);
        }
        Object proceed;
        try {
            proceed = invocation.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
            // DataSourceTypeManager.reset();
        }
        return proceed;
    }

    private RWSeparationDBContext getFinalRWSeparationDBContext(RWSeparationDBContext... rwSeparationDBContexts) {
        if (Objects.nonNull(rwSeparationDBContexts)) {
            for (RWSeparationDBContext context : rwSeparationDBContexts) {
                if (Objects.nonNull(context)) {
                    return context;
                }
            }
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {

        if (target instanceof Executor) {

            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
