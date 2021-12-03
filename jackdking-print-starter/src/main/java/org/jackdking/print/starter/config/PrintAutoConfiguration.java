package org.jackdking.print.starter.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.PrintService;

@Configuration
@ConditionalOnClass({PrintService.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(PrintProperties.class)
@Slf4j
public class PrintAutoConfiguration {

	  // 注入属性类
    @Autowired
    private PrintProperties printProperties;

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
    @ConditionalOnMissingBean(PrintService.class)
    public PrintService PrintService() {

    	PrintService printService = new PrintService();
    	printService.setPrintServiceName(printProperties);
        log.info("printProperties:{}", printProperties.toString());
        return printService;
    }

}
