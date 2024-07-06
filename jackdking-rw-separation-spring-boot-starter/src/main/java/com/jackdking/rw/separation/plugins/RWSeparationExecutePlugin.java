package com.jackdking.rw.separation.plugins;

import com.jackdking.rw.separation.annotation.RWSeparationDBType;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class RWSeparationExecutePlugin extends BaseInterceptor {


    /**
     * 可以识别为 添加、更新、删除类型的 SQL 语句
     */
    public static final List<SqlCommandType> UPDATE_SQL_LIST = Arrays.asList(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE);

    /**
     * SQL 语句中出现的悲观锁标识
     */
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
        Method[] mapperMethods = Class.forName(clazzStr).getMethods();
        Method targetMethod = null;
        for (Method mapperMethod : mapperMethods) {
            if (mapperMethod.getName().equals(methodStr)) {
                targetMethod = mapperMethod;
                break;
            }
        }

        // 获取 sqlCommandType
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        // id为执行的mapper方法的全路径名，如com.cq.UserMapper.insertUser， 便于后续使用反射
        String id = ms.getId();
        // 获取当前所拦截的方法名称
        String mName = id.substring(id.lastIndexOf(".") + 1);
        // 通过类全路径获取Class对象
        Class<?> classType = Class.forName(id.substring(0, id.lastIndexOf(".")));

        String dataSourceName = null;
        RWSeparationStrategyTypeEnum rwSeparationStrategyTypeEnum = RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;
        MethodOperationType operationType = MethodOperationType.WRITE;
        // 获取 SQL
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");

        if (sqlCommandType.equals(SqlCommandType.SELECT)) {
          operationType = MethodOperationType.READ;
        } else if (UPDATE_SQL_LIST.contains(sqlCommandType) || sql.contains(LOCK_KEYWORD)) {
          // 判断方法上是否带有自定义@RWSeparationDBType注解
          RWSeparationDBType rwSeparationDBType = targetMethod.getAnnotation(RWSeparationDBType.class);
          if (rwSeparationDBType != null) {
            log.debug("intercept func:{}, type:{}, origin SQL：{}", mName, sqlCommandType, sql);
            rwSeparationStrategyTypeEnum = rwSeparationDBType.rwStrategyType();
            dataSourceName = rwSeparationDBType.value();
            log.info("new SQL：{}", sql);
          }
        } else {
        }
        rwSeparationContext.decideWriteReadDs(dataSourceName, rwSeparationStrategyTypeEnum, operationType);

        Object proceed;
        try {
            proceed = invocation.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
//            DataSourceTypeManager.reset();
        }
        return proceed;
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
