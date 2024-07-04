package com.jackdking.rw.separation.plugins;

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

@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class RWSeparationInterceptor extends BaseInterceptor {


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

        // 获取 SQL
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");

        if (sqlCommandType.equals(SqlCommandType.SELECT)) {

//            DataSourceTypeManager.set(DataSources.slave);

        } else if (UPDATE_SQL_LIST.contains(sqlCommandType) || sql.contains(LOCK_KEYWORD)) {

//            DataSourceTypeManager.set(DataSources.slave);

        } else {
//            DataSourceTypeManager.set(DataSources.readmore);

        }

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