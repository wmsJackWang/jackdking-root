package org.jackdking.delay.domainv1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("延迟域系统服务").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("org.jackdking.delay.domainv1")).paths(PathSelectors.any()).build();
	}
	// 预览地址:http://localhost:8080/delay/swagger-ui.html
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("电商交易的架构师之路——延迟域").termsOfServiceUrl("https://bittechblog.com")
				.contact(new Contact("比特科技", "https://bittechblog.com", "jackdking@foxmail.com")).version("0.1.0").build();
	}
}
