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
import ace.conf.AceProperty;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AceInitFromNewInstanceStrategy
 * @Description TODO
 * @Author jackdking
 * @Date 11/12/2021 12:08 上午
 * @Version 2.0
 **/
@Slf4j
public class AceInitFromNewInstanceStrategy implements AceInitStrategy, ResourceLoaderAware {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    private AceProperty aceProperty;

    public AceInitFromNewInstanceStrategy(AceProperty aceProperty) {
        this.aceProperty = aceProperty;
    }

    @Override
    public void parseAnnoation() {
        /*
         * 解析包下的所有的校验器类
         */
        aceProperty.getAttributorsPath().stream().forEach(path -> {
            Set<Class<?>> attributors = doScan(path);
            attributors.stream().filter(c -> c.isAnnotationPresent(Attributor.class)).forEach(c -> {
                Attributor attributor = c.getAnnotation(Attributor.class);
                Object obj =  newInstance(c);
                if(Objects.isNull(obj))
                    System.out.println("attributor is null");
                System.out.println("attributor "+obj.getClass().getName());
                aceFactory.attributorMap.put(attributor.name(), (IAttributor) obj);
            });
        });

        aceProperty.getClassifiersPath().stream().forEach(path -> {
            Set<Class<?>> classifiers = doScan(path);
            classifiers.stream().filter(c -> c.isAnnotationPresent(Classifier.class)).forEach(c -> {
                Classifier classifier = c.getAnnotation(Classifier.class);
                aceFactory.classifierMap.put(classifier.name(),(IClassifier) newInstance(c));
            });
        });

        aceProperty.getExecutorsPath().stream().forEach(path -> {
            Set<Class<?>> executors = doScan(path);
            executors.stream().filter(c -> c.isAnnotationPresent(Executor.class)).forEach(c -> {
                Executor executor = c.getAnnotation(Executor.class);
                aceFactory.executorMap.put(executor.name(), (IExecutor) newInstance(c));
            });
        });

        Assert.notEmpty(aceFactory.attributorMap,"attributors is illegally empty");
        Assert.notEmpty(aceFactory.classifierMap,"classifier cannt be null");
        Assert.notEmpty(aceFactory.executorMap,"executor cannt be null");

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

    /**
     * description: 根据类class对象 来新建对象 <br>
     * version: 1.0 <br>
     * date: 14/08/2021 5:18 下午 <br>
     * author: jackdking <br>
     *
     * @param : 类class对象
     * @return: 返回创建好的对象
     **/
    private Object newInstance(Class<?> c) {
        try{
            return c.newInstance();
        }catch (Exception e){
            log.error("create instance fail :{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Spring容器注入
     */
    private ResourceLoader resourceLoader;

    private ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);

    /**
     * 利用spring提供的扫描包下面的类信息,再通过classfrom反射获得类信息
     *
     * @param scanPath
     * @throws IOException
     */
    public Set<Class<?>> doScan(String scanPath) {
        try{
            Set<Class<?>> classes = new HashSet<>();
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    .concat(ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(scanPath))
                            .concat("/**/*.class"));
            Resource[] resources = resolver.getResources(packageSearchPath);
            MetadataReader metadataReader = null;
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    try {
                        if (metadataReader.getClassMetadata().isConcrete()) {// 当类型不是抽象类或接口在添加到集合
                            classes.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return classes;
        }catch (IOException e){
            log.info("scan class fail :{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
