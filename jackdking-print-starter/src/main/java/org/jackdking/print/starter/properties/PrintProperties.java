package org.jackdking.print.starter.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "print")
public class PrintProperties {
	
	@Setter
	@Getter
	private List<ServiceConfig> serviceConfigs;
	
	@Data
	public static class ServiceConfig {
		
		private String serviceName;
		
	}
//	
//	private static PrintProperties  printProperties;
//	
//    @Autowired
//    public void setPrintProperties(PrintProperties printProperties) {
//    	PrintProperties.printProperties = printProperties;
//    }

}
