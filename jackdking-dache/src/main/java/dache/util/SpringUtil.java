package dache.util;

 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @类名    SpringUtil
 * @功能描述 普通类调用Spring bean对象;此类需要放到App.java同包或者子包下才能被扫描，否则失效。
 * @创建人      王洪会
 * @创建时间 2016年11月17日 下午2:05:43
 * @version V1.0
 * 
 */
@Component
public class SpringUtil implements ApplicationContextAware{
	private static Logger log = LogManager.getLogger(ApplicationContextAware.class);

       private static ApplicationContext applicationContext = null;
       @Override
       public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{

              if(SpringUtil.applicationContext == null){
                     SpringUtil.applicationContext  = applicationContext;
              }
       }

       //获取applicationContext
       public static ApplicationContext getApplicationContext() {
              return applicationContext;
       }

       //通过name获取 Bean.
       public static Object getBean(String name){
    	   log.info(name);
    	   log.info(getApplicationContext());
             return getApplicationContext().getBean(name);
       }
       //通过class获取Bean.
       public static <T> T getBean(Class<T> clazz){
             return getApplicationContext().getBean(clazz);
       }
       //通过name,以及Clazz返回指定的Bean
       public static <T> T getBean(String name,Class<T> clazz){
             return getApplicationContext().getBean(name, clazz);
       }
}