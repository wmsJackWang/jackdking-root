package annotation.reflection;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContextsUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(String beanName, Class<T> clazs) {
        return clazs.cast(getBean(beanName));
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}