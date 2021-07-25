package org.jackdking.delay.domainv1.infrastructure.util;
/*
 * Title HttpHelper.java
 * 提供发送http请求的通用方法 
 * 用法示例:
 * HttpHelper helper = HttpHelper.getInstance("http://somedomain.com/somurl");
 * helper.set("param1", "value1").set("param2", "value2"); // 链式添加请求参数
 * String getRes = helper.get(); // 发送get请求, 默认用utf-8将请求参数值转码
 * String postRes = helper.post(); // 发送post请求, 默认用utf-8将请求参数值转码
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 发送rest请求的工具类
 * @author Jackdking 
 * @since 2015-3-3 下午3:11:21
 */
public final class HttpHelper {
	
	private Logger log = LogManager.getLogger(HttpHelper.class);


	//get方式请求
	public static String httpRequestToString(String url, Map<String, String> params) {
		String result = null;
		try {
			InputStream is = httpRequestToStream(url, params);
			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	private static InputStream httpRequestToStream(String url, Map<String, String> params) {
		InputStream is = null;
		try {
			String parameters = "";
			boolean hasParams = false;
			for (String key : params.keySet()) {
				String value = URLEncoder.encode(params.get(key), "UTF-8");
				parameters += key + "=" + value + "&";
				hasParams = true;
			}
			if (hasParams) {
				parameters = parameters.substring(0, parameters.length() - 1);
			}

			url += "?" + parameters;

			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setDoInput(true);
			// 设置请求方式，默认为GET
			conn.setRequestMethod("GET");

			is = conn.getInputStream();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	//post方式请求
	public static String post(JSONObject json, String url){
		String result = "";
		HttpPost post = new HttpPost(url);
		try{
			CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        
			post.setHeader("Content-Type","application/json;charset=utf-8");
			post.addHeader("Authorization", "Basic YWRtaW46");
			StringEntity postingString = new StringEntity(json.toString(),"utf-8");
			post.setEntity(postingString);
			HttpResponse response = httpClient.execute(post);
			
			InputStream in = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuilder strber= new StringBuilder();
			String line = null;
			while((line = br.readLine())!=null){
				strber.append(line+'\n');
			}
			br.close();
			in.close();
			result = strber.toString();
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
				result = "服务器异常";
			}
		} catch (Exception e){
			System.out.println("请求异常");
			throw new RuntimeException(e);
		} finally{
			post.abort();
		}
		return result;
	}

	//无参get请求
	public static String get(String url){
		String result = "";
		HttpGet get = new HttpGet(url);
		try{
			CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
			
			HttpResponse response = httpClient.execute(get);
			result = getHttpEntityContent(response);
			
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
				result = "服务器异常";
			}
		} catch (Exception e){
			System.out.println("请求异常");
			throw new RuntimeException(e);
		} finally{
			get.abort();
		}
		return result;
	}
	
	//有参get请求
	public static String get(Map<String, String> paramMap, String url){
		String result = "";
		HttpGet get = new HttpGet(url);
		try{
			CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
			List<NameValuePair> params = setHttpParams(paramMap);
			String param = URLEncodedUtils.format(params, "UTF-8");
			get.setURI(URI.create(url + "?" + param));
			HttpResponse response = httpClient.execute(get);
			result = getHttpEntityContent(response);
			
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
				result = "服务器异常";
			}
		} catch (Exception e){
			System.out.println("请求异常");
			throw new RuntimeException(e);
		} finally{
			get.abort();
		}
		return result;
	}
	
	public static List<NameValuePair> setHttpParams(Map<String, String> paramMap){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for(Map.Entry<String, String> entry : set){
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return params;
	}
	
	public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException{
		String result = "";
		HttpEntity entity = response.getEntity();
		if(entity != null){
			InputStream in = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuilder strber= new StringBuilder();
			String line = null;
			while((line = br.readLine())!=null){
				strber.append(line+'\n');
			}
			br.close();
			in.close();
			result = strber.toString();
		}
		
		return result;
		
	}
	
}
