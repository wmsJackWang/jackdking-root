package spring.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;


@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> _class) {
        return applicationContext.getBean(beanName, _class);
    }

    public static <T> T getBean(String name) {
        T bean = (T) applicationContext.getBean(name);
        return bean;
    }

    public static <T> T getBean(Class<T> clazz) {
        T bean = applicationContext.getBean(clazz);
        return bean;
    }

    public static <T> T registerBean(String beanName, Class<T> beanClass) {

        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.setAllowBeanDefinitionOverriding(true);

        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(beanClass.getName());

        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);

        return getBean(beanName);
    }

    public static <T> T registerBean(Class<T> beanClass) {

        return registerBean(beanClass.getName(), beanClass);
    }

    public static <T> T registerOrGet(String beanName, Class<T> beanClass) {
        T bean = getBean(beanName);
        if (Objects.isNull(bean)) {
            bean = registerBean(beanName, beanClass);
        }

        return bean;
    }

}
