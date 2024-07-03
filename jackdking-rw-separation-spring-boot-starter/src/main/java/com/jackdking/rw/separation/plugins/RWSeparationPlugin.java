package com.jackdking.rw.separation.plugins;

import com.jackdking.rw.separation.annotation.InterceptAnnotation;
import com.jackdking.rw.separation.annotation.RWSeparationDBType;
import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.enums.MethodOperationType;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import com.jackdking.rw.separation.strategy.RWSeparationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Component
//拦截StatementHandler类中参数类型为Statement的prepare方法（prepare=在预编译SQL前加入修改的逻辑）
//即拦截 Statement prepare(Connection var1, Integer var2) 方法
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class RWSeparationPlugin implements Interceptor {

    @Autowired
    RWSeparationContext rwSeparationContext;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {       // 获取原始sql
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        // 通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性;：MetaObject是Mybatis提供的一个用于方便、
        // 优雅访问对象属性的对象，通过它可以简化代码、不需要try/catch各种reflect异常，同时它支持对JavaBean、Collection、Map三种类型对象的操作。
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,new DefaultReflectorFactory());
        // 先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 通过反射，拦截方法上带有自定义@InterceptAnnotation注解的方法，并修改sql
        String newSql = sqlAnnotationEnhance(mappedStatement, boundSql);

        bindNewSql(newSql, boundSql);
        return invocation.proceed();
    }

    private void bindNewSql(String newSql, BoundSql boundSql) throws Throwable {
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 通过反射，拦截方法上带有自定义@InterceptAnnotation注解的方法，并增强sql
     * @param mappedStatement
     * @param boundSql
     * @return
     * @throws ClassNotFoundException
     */
    private String sqlAnnotationEnhance(MappedStatement mappedStatement, BoundSql boundSql) throws ClassNotFoundException {
        // 获取到原始sql语句
        String sql = boundSql.getSql().toLowerCase();
        // sql语句类型 select、delete、insert、update
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();

        // id为执行的mapper方法的全路径名，如com.cq.UserMapper.insertUser， 便于后续使用反射
        String id = mappedStatement.getId();
        // 获取当前所拦截的方法名称
        String mName = id.substring(id.lastIndexOf(".") + 1);
        // 通过类全路径获取Class对象
        Class<?> classType = Class.forName(id.substring(0, id.lastIndexOf(".")));

        // 获得参数集合
        String paramString = null;
        if (boundSql.getParameterObject() != null) {
            paramString = boundSql.getParameterObject().toString();
        }
        String dataSourceName = null;
        RWSeparationStrategyTypeEnum rwSeparationStrategyTypeEnum = RWSeparationStrategyTypeEnum.RW_SEPARATION_ONLY_MASTER;
        MethodOperationType operationType = MethodOperationType.WRITE;
        DynamicDataSourceHolder.clearType();//提前清理数据库类型

        // 遍历类中所有方法名称，并匹配上当前所拦截的方法
        for (Method method : classType.getDeclaredMethods()) {
            if (mName.equals(method.getName())) {
                if ("select".equals((sqlCommandType.toLowerCase()))) {
                    operationType = MethodOperationType.READ;
                }

                // 判断方法上是否带有自定义@InterceptAnnotation注解
                RWSeparationDBType rwSeparationDBType = method.getAnnotation(RWSeparationDBType.class);
                if (rwSeparationDBType != null) {
                    log.debug("intercept func:{}, type:{}, origin SQL：{}", mName, sqlCommandType, sql);
                    rwSeparationStrategyTypeEnum = rwSeparationDBType.rwStrategyType();
                    dataSourceName = rwSeparationDBType.value();
                    log.info("new SQL：{}", sql);
                }
                rwSeparationContext.decideWriteReadDs(dataSourceName, rwSeparationStrategyTypeEnum, operationType);
            }
        }
        return sql;
    }

    String getValue(String param, String key) {
        if (param == null) {
            return null;
        }
        String[] keyValuePairs = param.substring(1, param.length() - 1).split(",");
        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            if (entry[0].trim().equals(key)) {
                return entry[1].trim();
            }
        }
        return null;
    }
}
