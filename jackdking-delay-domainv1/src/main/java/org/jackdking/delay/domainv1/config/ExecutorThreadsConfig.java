package org.jackdking.delay.domainv1.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class ExecutorThreadsConfig {
	
	@Value("${executor.keepalivetime}")
	String keepalivetime;
	
	@Value("${executor.corepoolsize}")
	String corepoolsize;
	
	@Value("${executor.maxpoolsize}")
	String maxpoolsize;
	
	@Value("${executor.queuecapacity}")
	String queuecapacity;
	
	@Value("${executor.allow}")
	String allow;
}
