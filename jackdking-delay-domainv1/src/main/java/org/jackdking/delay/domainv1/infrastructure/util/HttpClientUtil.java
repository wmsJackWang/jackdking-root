package org.jackdking.delay.domainv1.infrastructure.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

//单利模式维护httpclient
public class HttpClientUtil {

    /** 链接超时时间 **/
    private static final Integer CONNECT_TIME_OUT = 40 * 1000;

    /** 数据传输超时时间 **/
    private static final Integer SOCKET_TIME_OUT = 40 * 1000;
    
    private static CloseableHttpClient httpClient = null;
    
    private static Object ObjectLock = new Object();

    public static CloseableHttpClient getHttpClient(){
    	if(httpClient!=null)
    	{
    		synchronized (ObjectLock) {
    			if(httpClient!=null)
    			{
			        RequestConfig.Builder configBuilder = RequestConfig.custom();
			        configBuilder.setConnectTimeout(CONNECT_TIME_OUT);
			        configBuilder.setSocketTimeout(SOCKET_TIME_OUT);
			
			        HttpClientBuilder clientBuilder = HttpClients.custom();
			        clientBuilder.setDefaultRequestConfig(configBuilder.build());
			        httpClient = clientBuilder.build();
    			}
    		}
    	}
    	else
    		return httpClient;

        return httpClient;
    }

}
