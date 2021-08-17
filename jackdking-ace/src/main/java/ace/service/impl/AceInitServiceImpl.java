package ace.service.impl;

import ace.annoation.Attributor;
import ace.annoation.Classifier;
import ace.annoation.Executor;
import ace.annoation.Ruler;
import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.constant.Constants;
import ace.service.AceInitService;
import ace.core.AceWorker;
import ace.enums.ErrorMessageCode;
import ace.executor.IExecutor;
import ace.factory.AceFactory;
import ace.utils.AceUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.core.io.Resource;

import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
@Order(Integer.MIN_VALUE)
public class AceInitServiceImpl implements AceInitService, ApplicationListener<ContextRefreshedEvent> , ApplicationRunner , ResourceLoaderAware {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    @Autowired
    private AceProperty aceProperty;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {


        boolean isAtomicClassifier = true;
        boolean isNestClassifier = true;
        Assert.isTrue(isAtomicClassifier^isNestClassifier, ErrorMessageCode.CLASSIFIER_ILLEGAL.retCheckMessage("classifierName"));
//
//
//        IAttributor attributor = (IAttributor) Class.forName("ace.attributor.TopicTagAttributor").newInstance();
//        System.out.println(JSON.toJSONString(attributor));
//
//        Annotation annotation = AnnotationUtils.getAnnotation(TopicTagAttributor.class, Attributor.class);
//        System.out.println("name:"+((Attributor)annotation).name());
//        System.out.println("fieldName:"+((Attributor)annotation).fieldName());
//
//        Attributor attributor = AnnotationUtils.getAnnotation(TopicTagAttributor.class, Attributor.class);
//        System.out.println(attributor.name()+":"+attributor.fieldName());
//        Object obj = new TopicTagAttributor();
//
//        List<Method> methods = Lists.newArrayList(ReflectionUtils.getAllDeclaredMethods(TopicTagAttributor.class)).
//                stream().filter((m)->{return m.isAnnotationPresent(Ruler.class);}).collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(methods)){
//            System.out.println("method no exists");
//        }
//        methods.forEach(method ->System.out.println(method.getName()));
//        Map<String,Method> methodMap = Maps.uniqueIndex(methods, new Function<Method, String>() {
//            @Override
//            public String apply(Method input) {
//                return input.getAnnotation(Ruler.class).name();
//            }
//        });
//
//        Map<String,Object> contextDataParam = ImmutableMap.<String,Object>builder()
//                .put(Constants.ACE_CONTEXT_PARAM,"refund")
//                .put(Constants.TAG,"refund")
//                .build();
//
//        AceContext aceContext = AceContext.of(contextDataParam,"checkTopicTag");
//        Method ruler1 = methodMap.get("checkTopicTag");
//        Object result = ReflectionUtils.invokeMethod(ruler1,obj,aceContext);
//        AceResult aceResult = (AceResult) result;
//        System.out.println("校验器检车结果："+aceResult.getIsEffect());

    }

    @Override
    public void init() {
        if(initialized.get()) {
            log.info("Ace already init");
            return;
        }
        initialized.set(true);
        log.debug(aceProperty.toString());
        Assert.notEmpty(aceProperty.getAttributorsPath(),"AttributorsPath cannt be empty");
        Assert.notEmpty(aceProperty.getClassifiersPath(),"ClassifiersPath cannt be empty");
        Assert.notEmpty(aceProperty.getExecutorsPath(),"ExecutorsPath cannt be empty");
        parseAnnoation();
        parseAttributor();
        parseClassifier();
        parseExecutor();
        complete();
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

        log.debug("attributorMap size：{},content:{}",aceFactory.attributorMap.size(),JSON.toJSONString(aceFactory.attributorMap));
        log.debug("classifierMap size：{},content:{}",aceFactory.classifierMap.size(),JSON.toJSONString(aceFactory.classifierMap));
        log.debug("executorMap size：{},content:{}",aceFactory.executorMap.size(),JSON.toJSONString(aceFactory.executorMap));
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

    /*
     * classifier解析
     */
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
                        aceFactory.classifierRulerParamMap.put(AceUtil.getClassifierRulerName(classifier.name(),ruler.name(),Constants.FILTER),ruler.value());
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

    private void validateLegalParam(String classifierName, Ruler[] matchers, Ruler[] filters, String priority) {

        boolean isAtomicClassifier = !ObjectUtils.isEmpty(matchers) || !ObjectUtils.isEmpty(filters);
        boolean isNestClassifier = !StringUtils.isEmpty(priority);
        Assert.isTrue(isAtomicClassifier^isNestClassifier, ErrorMessageCode.CLASSIFIER_ILLEGAL.retCheckMessage(classifierName));
    }

    @Override
    public void parseExecutor() {
//        aceFactory.executorMap.entrySet().stream().distinct().forEach(c -> {
//            Executor executor = c.getValue().getClass().getAnnotation(Executor.class);
//            log.debug("executor {} begin to parse",executor.name());
//            aceFactory.executorMap.put(executor.name(), (IExecutor) newInstance(c.getValue().getClass()));
//        });
    }

    @Override
    public void complete() {
        log.info("ace component init completed ");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("spring refresh, reload ace component");
        initialized.set(false);
        init();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ace component start init");
        init();
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
            Set<Class<?>> classes = new HashSet<Class<?>>();
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


    @ConfigurationProperties(prefix = "ace")
    @Component
    @Data
    class AceProperty{
        private List<String> attributorsPath;
        private List<String> classifiersPath;
        private List<String> executorsPath;
    }
}
