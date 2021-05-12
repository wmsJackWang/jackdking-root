package org.jackdking.delay.domainv1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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

	public String getKeepalivetime() {
		return keepalivetime;
	}

	public void setKeepalivetime(String keepalivetime) {
		this.keepalivetime = keepalivetime;
	}

	public String getCorepoolsize() {
		return corepoolsize;
	}

	public void setCorepoolsize(String corepoolsize) {
		this.corepoolsize = corepoolsize;
	}

	public String getMaxpoolsize() {
		return maxpoolsize;
	}

	public void setMaxpoolsize(String maxpoolsize) {
		this.maxpoolsize = maxpoolsize;
	}

	public String getQueuecapacity() {
		return queuecapacity;
	}

	public void setQueuecapacity(String queuecapacity) {
		this.queuecapacity = queuecapacity;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	@Override
	public String toString() {
		return "ExecutorThreadsConfig [keepalivetime=" + keepalivetime + ", corepoolsize=" + corepoolsize
				+ ", maxpoolsize=" + maxpoolsize + ", queuecapacity=" + queuecapacity + ", allow=" + allow + "]";
	}
	
}
