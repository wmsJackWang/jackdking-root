package springboot.MySolution_dynamicdatasource.aspect;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Before("@annotation(dbType)")
    public void changeDataSourceType(JoinPoint joinPoint, DBType dbType){
    	
        DataSourceType curType = dbType.value();
        if(!DynamicDataSourceHolder.containsType(curType)){
            logger.info("指定数据源[{}]不存在，使用默认数据源-> {}",dbType.value(),joinPoint.getSignature());
        }else{
            logger.info("use datasource {} -> {}",dbType.value(),joinPoint.getSignature());
            DynamicDataSourceHolder.setType(dbType.value());
        }

    }

    @After("@annotation(dbType)")
    public void restoreDataSource(JoinPoint joinPoint, DBType dbType){
        logger.info("remove datasource {} -> {}",dbType.value(),joinPoint.getSignature());
        DynamicDataSourceHolder.clearType();
    }

}