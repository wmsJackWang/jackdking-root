package com;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
//import org.jackdking.print.starter.service.PrintService;
import org.jackdking.print.starter.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Configuration
@Slf4j
public class ApplicationStarter implements ApplicationRunner
{
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Autowired
    PrintService printService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//      ReportStarterAutoConfiguration test = new ReportStarterAutoConfiguration();
      com.anjiplus.template.gaea.business.config.ReportProperties properties = new com.anjiplus.template.gaea.business.config.ReportProperties();
      log.info("properties:{}", JSON.toJSONString(printService.getPrintServiceName()));
    }
}
