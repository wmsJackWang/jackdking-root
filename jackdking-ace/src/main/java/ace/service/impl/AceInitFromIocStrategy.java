package ace.service.impl;

import ace.annoation.Attributor;
import ace.annoation.Classifier;
import ace.annoation.Executor;
import ace.annoation.Ruler;
import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.constant.Constants;
import ace.core.AceWorker;
import ace.executor.IExecutor;
import ace.factory.AceFactory;
import ace.service.AceInitStrategy;
import ace.utils.AceUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AceInitFromIocStrategy
 * @Description TODO
 * @Author jackdking
 * @Date 11/12/2021 12:09 上午
 * @Version 2.0
 **/
@Slf4j
public class AceInitFromIocStrategy implements AceInitStrategy , ApplicationContextAware {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    private ApplicationContext applicationContext;

    public void parseAnnoation() {

        Map<String, IAttributor> attributorBeanMaps = applicationContext.getBeansOfType(IAttributor.class);
        attributorBeanMaps.forEach((beanName, attributor) -> {

            Attributor attributorAnnoation = attributor.getClass().getAnnotation(Attributor.class);
            aceFactory.attributorMap.put(attributorAnnoation.name(), attributor);

        });

        Map<String, IClassifier> classifierBeanMap = applicationContext.getBeansOfType(IClassifier.class);
        classifierBeanMap.forEach((beanName, classifier) -> {

            Classifier classifierAnnoation = classifier.getClass().getAnnotation(Classifier.class);
            aceFactory.classifierMap.put(classifierAnnoation.name(), classifier);

        });

        Map<String, IExecutor> executorBeanMap = applicationContext.getBeansOfType(IExecutor.class);
        executorBeanMap.forEach((beanName, executor) -> {

            Executor executorAnnoation = executor.getClass().getAnnotation(Executor.class);
            aceFactory.executorMap.put(executorAnnoation.name(), executor);

        });

        log.debug("attributorMap size：{},content:{}",aceFactory.attributorMap.size(), JSON.toJSONString(aceFactory.attributorMap));
        log.debug("classifierMap size：{},content:{}",aceFactory.classifierMap.size(),JSON.toJSONString(aceFactory.classifierMap));
        log.debug("executorMap size：{},content:{}",aceFactory.executorMap.size(),JSON.toJSONString(aceFactory.executorMap));
    }

    @Override
    public void parseAttributor() {

        /*
         * 解析 属性校验器的规则
         */
        Assert.notEmpty(aceFactory.attributorMap,"attributorsMap is illegally empty");
        aceFactory.attributorMap.entrySet().stream().distinct().forEach(attributor -> {
            Method[] methods = attributor.getValue().getClass().getMethods();
            if(methods==null||methods.length==0) {
                return;
            }

            Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Ruler.class)) .forEach(method -> {
                Ruler ruler = method.getAnnotation(Ruler.class);
                aceFactory.rulerMap.put(ruler.name(),method);
                aceFactory.ruler2AttributorMap.put(ruler.name(),attributor.getValue());
                String values[] = ruler.value();
                //根据规则注解中的value 来赋予新的值。
                if(values!=null && values.length>0) {
                    aceFactory.rulerParamMap.put(ruler.name(),values);
                    return;
                }
            });
        });
        log.debug("rulerMap size：{}",aceFactory.rulerMap.size());
        log.debug("rulerParamMap size：{}",aceFactory.rulerParamMap.size());
    }

    @Override
    public void parseClassifier() {

        aceFactory.classifierMap.entrySet().stream().distinct().forEach(c -> {
            Classifier classifier = c.getValue().getClass().getAnnotation(Classifier.class);
            log.debug("classifier {} begin to parse",classifier.name());
            Ruler[] matchers = classifier.matcher();
            Ruler[] filters = classifier.filter();
            String priority = classifier.priority();

            validateLegalParam(classifier.name(), matchers, filters, priority);

            //filters  can be empty
            if (!ObjectUtils.isEmpty(filters)) {
                Arrays.stream(filters).forEach(ruler -> {
                    aceFactory.classifierFilters.put(classifier.name(),ruler.name());
                    //规则值参数如果不为空 则使用默认的值参数
                    if (!ObjectUtils.isEmpty(ruler.value())) {
                        aceFactory.classifierRulerParamMap.put(AceUtil.getClassifierRulerName(classifier.name(),ruler.name(), Constants.FILTER),ruler.value());
                    }else {
                        aceFactory.classifierRulerParamMap.put(AceUtil.getClassifierRulerName(classifier.name(),ruler.name(),Constants.FILTER),aceFactory.rulerParamMap.get(ruler));
                    }
                });
            }

            Arrays.stream(matchers).forEach(ruler -> {
                log.debug("put classifierMatchers  key:{} ，value:{}",classifier.name(),ruler.name());
                aceFactory.classifierMatchers.put(classifier.name(),ruler.name());
                //规则值参数如果不为空 则使用默认的值参数
                if (!ObjectUtils.isEmpty(ruler.value())) {
                    aceFactory.classifierRulerParamMap.put(AceUtil.getClassifierRulerName(classifier.name(),ruler.name(),Constants.MATCHER),ruler.value());
                }else {
                    aceFactory.classifierRulerParamMap.put(AceUtil.getClassifierRulerName(classifier.name(),ruler.name(),Constants.MATCHER),aceFactory.rulerParamMap.get(ruler));
                }
            });

            if (!StringUtils.isEmpty(priority)) {
                aceFactory.classifierPriority.put(classifier.name(),priority);
            }
            log.debug("classifier[{}] load success",classifier.name());
        });
    }

    @Override
    public void parseExecutor() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
