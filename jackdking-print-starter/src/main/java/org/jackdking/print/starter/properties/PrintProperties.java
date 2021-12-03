package org.jackdking.print.starter.properties;

import java.util.List;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "print")
@ToString
public class PrintProperties {
	
	@Setter
	@Getter
	private List<ServiceConfig> serviceConfigs;

	@Data
	@ToString
	public static class ServiceConfig {
		
		private String serviceName;

	}
}
