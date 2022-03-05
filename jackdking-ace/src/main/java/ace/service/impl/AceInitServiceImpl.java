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
import ace.service.AceInitStrategy;
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


/*
 * 设计有@enable机制来控制策略， 配置propertity属性来控制
 */
@Slf4j
@Service
@Order(Integer.MIN_VALUE)
public class AceInitServiceImpl implements AceInitService, ApplicationListener<ContextRefreshedEvent> , ApplicationRunner {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    @Autowired
    private AceProperty aceProperty;

    private AceInitStrategy aceInitStrategy;

    public AceInitServiceImpl(AceInitStrategy aceInitStrategy) {
        this.aceInitStrategy = aceInitStrategy;
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
        aceInitStrategy.parseAnnoation();
        aceInitStrategy.parseAttributor();
        aceInitStrategy.parseClassifier();
        aceInitStrategy.parseExecutor();
        complete();
    }

    @Override
    public void complete() {
        log.info("ace component init completed ");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("spring refresh, reload ace component");

        //如果spring方式开启，则开始加载RACE归因组件

        
        initialized.set(false);
        init();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ace component start init");
        init();
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
