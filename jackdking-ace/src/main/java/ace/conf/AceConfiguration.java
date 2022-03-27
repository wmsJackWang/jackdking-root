package ace.conf;

import ace.service.impl.AceInitFromIocStrategy;
import ace.service.impl.AceInitFromNewInstanceStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AceConfiguration
 * @Description TODO
 * @Author jackdking
 * @Date 11/12/2021 6:14 下午
 * @Version 2.0
 **/
@Configuration
@Slf4j
public class AceConfiguration {

    @ConfigurationProperties(prefix = "ace")
    @Bean
    public AceProperty aceProperty() {
        return new AceProperty();
    }

    @Bean
    @ConditionalOnProperty(prefix = "ace.strategy", name = "scanPackage", havingValue = "true")
    public AceInitFromNewInstanceStrategy fromNewInstanceStrategy(AceProperty aceProperty) {
        log.info("enable AceInitFromNewInstanceStrategy, then scan ioc to init Ace Component");
        AceInitFromNewInstanceStrategy fromNewInstanceStrategy = new AceInitFromNewInstanceStrategy(aceProperty);
        return fromNewInstanceStrategy;
    }

    @Bean
    @ConditionalOnProperty(prefix = "ace.strategy", name = "scanIoc", havingValue = "true")
    public AceInitFromIocStrategy fromIocStrategy() {
        log.info("enable AceInitFromIocStrategy, then scan ioc to init Ace Component");
        AceInitFromIocStrategy fromIocStrategy = new AceInitFromIocStrategy();
        return fromIocStrategy;
    }

}
