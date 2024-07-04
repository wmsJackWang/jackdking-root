package com.jackdking.rw.separation.plugins;

import com.jackdking.rw.separation.config.Constants;
import com.jackdking.rw.separation.strategy.RWSeparationContext;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public abstract class BaseInterceptor implements ApplicationRunner, ApplicationContextAware, Interceptor {

    RWSeparationContext rwSeparationContext;

    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        rwSeparationContext = (RWSeparationContext) applicationContext.getBean(Constants.SEPARATION_CONTEXT_BEAN_NAME);
        Map<String, SqlSessionFactoryBean> sqlSessionFactoryBeanList = applicationContext.getBeansOfType(SqlSessionFactoryBean.class);
        Optional.ofNullable(sqlSessionFactoryBeanList).orElseGet(Collections::emptyMap)
                .forEach((s, sqlSessionFactoryBean) -> sqlSessionFactoryBean.setPlugins(this));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
