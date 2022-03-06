package core.spring.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

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
public class BeanDefinitionRegistryPostProcessorTest implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
    {
        // 新增
        BeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClassName("Student");
        registry.registerBeanDefinition("student",bd);
        // 查询和修改
        BeanDefinition student = registry.getBeanDefinition("student");
        // 删除
        registry.removeBeanDefinition("student");
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
}
