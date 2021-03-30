package springboot.MySolution_RWseparation.mybatis.dynamicdatasource;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author mingshengwang
 * @date 2019年10月29日 下午4:43:49
 * @todo TODO
 * @descript null
 */
@Aspect
@Component
public class RwSeparateDataSourceAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(RwSeparateDataSourceAspect.class);
//    @Autowired
//    @Qualifier("sqlSessionFactory")
//    private SqlSessionFactory sqlSessionFactory;
//
//    // first * any return value
//    // second * any class name of the dao package
//    // third * any method name of the class
//    // (..) any number of parameters
//    @Pointcut("execution(* org.jackdking.core.dao.*.*(..))")
//    public void declareJoinPoint(){
//    }
//
//    /**
//     * @author mingshengwang
//     * @date 2019年10月29日
//     * @return void
//     * @parameter null
//     * @description  根据mapper方法执行的sql类型来进行选择不同的数据库。
//     * @param point
//     */
//    @Before("declareJoinPoint()")
//    public void matchDataSoruce(JoinPoint point){
//
//        //得到被代理对象
//        Object target = point.getTarget();
//        //目标方法名
//        String methodName = point.getSignature().getName();
//
//        Class<?>[] interfaces = target.getClass().getInterfaces();
//        Class<?>[] parametersTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
//
//        try {
//        	System.out.println("读写分离判断。。。");
//            Method method = interfaces[0].getMethod(methodName,parametersTypes);
//            String key = interfaces[0].getName() + "." + method.getName();
//            //sql 的类型
//            SqlCommandType type = sqlSessionFactory.getConfiguration().getMappedStatement(key).getSqlCommandType();
//            //查询从库 增删改主库
//            if(type == SqlCommandType.SELECT){
//                logger.info("use slaveDataSource");
//                DynamicDataSourceHolder.setType(DataSourceType.SLAVE);
//            }
//            if(type == SqlCommandType.DELETE || type == SqlCommandType.UPDATE || type == SqlCommandType.INSERT){
//                logger.info("use masterDataSource");
//                DynamicDataSourceHolder.setType(DataSourceType.MASTER);
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After("declareJoinPoint()")
//    public void restoreDataSource(JoinPoint joinPoint){
//        logger.info(DynamicDataSourceHolder.getType() + " change to master" + joinPoint.getSignature());
//        DynamicDataSourceHolder.clearType();
//    }
}