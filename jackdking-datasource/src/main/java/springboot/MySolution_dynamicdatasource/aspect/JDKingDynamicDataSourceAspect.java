package springboot.MySolution_dynamicdatasource.aspect;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.DataSourceType;
import springboot.MySolution_RWseparation.mybatis.dynamicdatasource.DynamicDataSourceHolder;
import springboot.MySolution_dynamicdatasource.annotation.DBType;

@Aspect
@Component
public class JDKingDynamicDataSourceAspect  {

    private static final Logger logger = LoggerFactory.getLogger(JDKingDynamicDataSourceAspect.class);

    @Around("@annotation(dbType)")
    public Object changeDataSourceType(ProceedingJoinPoint joinPoint, DBType dbType) throws Throwable {

        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();

        DataSourceType curType = dbType.value();
        if(!DynamicDataSourceHolder.containsType(curType)){
            logger.info("指定数据源[{}]不存在，使用默认数据源-> {}",dbType.value(),joinPoint.getSignature());
        }else{
            logger.info("use datasource {} -> {}",dbType.value(),joinPoint.getSignature());
            DynamicDataSourceHolder.setType(dbType.value());
        }
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        
        logger.info("remove datasource {} -> {}",dbType.value(),joinPoint.getSignature());
        DynamicDataSourceHolder.clearType();
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

}