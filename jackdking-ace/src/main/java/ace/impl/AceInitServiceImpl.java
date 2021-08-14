package ace.impl;

import ace.*;
import ace.annoation.Attributor;
import ace.annoation.Classifier;
import ace.annoation.Ruler;
import ace.attributor.IAttributor;
import ace.attributor.TopicTagAttributor;
import ace.classifier.IClassifier;
import ace.factory.AceFactory;
import com.google.common.base.Function;
import com.google.common.collect.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AceInitServiceImpl implements AceInitService , ApplicationListener<ContextRefreshedEvent> , ApplicationRunner , ResourceLoaderAware {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    @Autowired
    private AceProperty aceProperty;

    public static void main(String[] args) {

        Annotation annotation = AnnotationUtils.getAnnotation(TopicTagAttributor.class, Attributor.class);
        System.out.println("name:"+((Attributor)annotation).name());
        System.out.println("fieldName:"+((Attributor)annotation).fieldName());

        Attributor attributor = AnnotationUtils.getAnnotation(TopicTagAttributor.class, Attributor.class);
        System.out.println(attributor.name()+":"+attributor.fieldName());
        Object obj = new TopicTagAttributor();

        List<Method> methods = Lists.newArrayList(ReflectionUtils.getAllDeclaredMethods(TopicTagAttributor.class)).
                stream().filter((m)->{return m.isAnnotationPresent(Ruler.class);}).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(methods)){
            System.out.println("method no exists");
        }
        methods.forEach(method ->System.out.println(method.getName()));
        Map<String,Method> methodMap = Maps.uniqueIndex(methods, new Function<Method, String>() {
            @Override
            public String apply(Method input) {
                return input.getAnnotation(Ruler.class).name();
            }
        });

        Map<String,Object> contextDataParam = ImmutableMap.<String,Object>builder()
                .put(Constants.ACE_CONTEXT_PARAM,"refund")
                .put(Constants.TAG,"refund")
                .build();

        AceContext aceContext = AceContext.of(contextDataParam,"checkTopicTag");
        Method ruler1 = methodMap.get("checkTopicTag");
        Object result = ReflectionUtils.invokeMethod(ruler1,obj,aceContext);
        AceResult aceResult = (AceResult) result;
        System.out.println("校验器检车结果："+aceResult.getIsEffect());

    }

    @Override
    public void init() {
        if(initialized.get()) {
            log.info("Ace already init");
            return;
        }
        log.info(aceProperty.toString());
        Assert.notEmpty(aceProperty.getAttributorsPath(),"AttributorsPath cannt be empty");
        Assert.notEmpty(aceProperty.getClassifiersPath(),"ClassifiersPath cannt be empty");
        Assert.notEmpty(aceProperty.getExecutorsPath(),"ExecutorsPath cannt be empty");

        Multimap<String,String> annotationsMap = parseAnnoation();

    }

    @Override
    public Multimap<String,String> parseAnnoation() {
        Multimap<String,String> annotationsMap = LinkedListMultimap.create();

        aceProperty.getAttributorsPath().stream().forEach(path->{
            Set<Class<?>> attributors = doScan(path);
            attributors.stream().forEach(c -> {
                Attributor attributor = c.getAnnotation(Attributor.class);
                aceFactory.attributorMap.put(attributor.name(), (IAttributor) newInstance(c));
            });
            Assert.notEmpty(aceFactory.attributorMap,"attributorsMap is illegally empty");
            aceFactory.attributorMap.entrySet().stream().distinct().forEach(attributor -> {
                Method[] methods = attributor.getValue().getClass().getMethods();
                if(methods==null||methods.length==0) {
                    return;
                }
                log.info("methods size:{}",methods.length);
                Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Ruler.class)) .forEach(method -> {
                    Ruler ruler = method.getAnnotation(Ruler.class);
                    aceFactory.rulerMap.put(ruler.name(),method);
                    String values[] = ruler.value();
                    //根据规则注解中的value 来赋予新的值。
                    if(values!=null && values.length>0) {
                        aceFactory.rulerParamMap.put(ruler.name(),values);
                        return;
                    }
                });
            });
        });

        aceProperty.classifiersPath.stream().forEach(path -> {
            Set<Class<?>> classifiers = doScan(path);
            classifiers.stream().forEach(c -> {
                Classifier classifier = c.getAnnotation(Classifier.class);
                aceFactory.classifierMap.put(classifier.name(), (IClassifier) newInstance(c));

                Ruler[] matcher = classifier.matcher();//匹配规则集合
                Ruler[] filter  = classifier.filter();//过滤规则集合

                Assert.notEmpty(matcher,"分类器"+classifier.name()+"匹配规则 不能为空");



            });
            Assert.notEmpty(aceFactory.classifierMap,"classifierMap is illegally empty");


        });


        log.info("attributorMap size：{}",aceFactory.attributorMap.size());
        log.info("rulerMap size：{}",aceFactory.rulerMap.size());
        log.info("rulerParamMap size：{}",aceFactory.rulerParamMap.size());
        return null;
    }

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

    }

    @Override
    public void parseClassifier() {

    }

    @Override
    public void parseExecutor() {

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
