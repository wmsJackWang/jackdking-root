package spring.core.spring.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BeanDefinitionRegistryPostProcessorTest
 * @Description TODO
 * @Author jackdking
 * @Date 04/03/2022 7:21 下午
 * @Version 2.0
 **/
@Component
public class BeanDefinitionRegistryPostProcessorTest implements BeanDefinitionRegistryPostProcessor, PriorityOrdered , EnvironmentAware {


    /*
     * beanDefinition注册顺序不会影响bean的依赖注入的，注册时候所有bean都还未初始化，也没有设置好依赖关系。
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
    {

        System.out.println("开始注册bean");

        // 新增
        BeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClassName("domain.Teacher");
        registry.registerBeanDefinition("teacher",bd);

        // 新增
        bd = new GenericBeanDefinition();
        bd.setBeanClassName("domain.Student");
        registry.registerBeanDefinition("student",bd);
        // 查询和修改
        BeanDefinition student = registry.getBeanDefinition("student");


//        BeanDefinition springContext = registry.getBeanDefinition("SpringContextUtils");
//        System.out.println(springContext.getBeanClassName());
        // 删除
//        registry.removeBeanDefinition("student");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        System.out.println("============= BeanDefinitionRegistryPostProcessorTest invoke beanFactory =========");
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment.getProperty("tes.maps", HashMap.class));
    }
}
