package ace.conf;

import ace.service.impl.AceInitFromIocStrategy;
import ace.service.impl.AceInitFromNewInstanceStrategy;
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
public class AceConfiguration {

    @ConfigurationProperties(prefix = "ace")
    @Bean
    public AceProperty aceProperty() {
        return new AceProperty();
    }

    @Bean
    @ConditionalOnProperty(prefix = "ace", name = "strategy", havingValue = "scanPackage")
    public AceInitFromNewInstanceStrategy fromNewInstanceStrategy(AceProperty aceProperty) {
        AceInitFromNewInstanceStrategy fromNewInstanceStrategy = new AceInitFromNewInstanceStrategy(aceProperty);
        return fromNewInstanceStrategy;
    }

    @Bean
    @ConditionalOnProperty(prefix = "ace", name = "strategy", havingValue = "scanIoc")
    public AceInitFromIocStrategy fromIocStrategy() {
        AceInitFromIocStrategy fromIocStrategy = new AceInitFromIocStrategy();
        return fromIocStrategy;
    }

}
