package org.jackdking.shardjdbcyaml.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestController {
	
	private static   String url = "http://localhost:8080/jsonController";
    private static   String charset = "utf-8";
//    private static final  CloseableHttpClient httpClient = HttpClients.createDefault();

    
    
//    @Test
	public static void testTransOneParamObject() throws ClientProtocolException, IOException {
		for(int i = 0 ; i < 1 ; ++i)
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				url = "http://localhost:8080/jsonController";
				charset = "utf-8";
				CloseableHttpClient httpClient = HttpClients.createDefault();
				String methodMapping = "/transTB";
				String urltest = url;
				urltest += methodMapping;
				
				//设置访问的url
				HttpPost httpPost = new HttpPost(urltest);
				//设置传递的数据 
				String userid = "1042164771@qq.com";
				Long orderid = 12312342131312L;
				java.sql.Timestamp ordertime = new java.sql.Timestamp(System.currentTimeMillis());
				int num = 0;
				long starttime = System.currentTimeMillis();
				do {
					
				++num;
				userid = "userid:"+(new Date()).getTime();
				orderid =(new Date()).getTime();
				ordertime = new java.sql.Timestamp(System.currentTimeMillis());
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//将参数暂时存放到map中去
				Map<String,Object> map = new HashMap<String,Object>();
		        map.put("userid",userid);
		        map.put("orderid",orderid);
		        map.put("ordertime" , sdf.format(ordertime));
		      
//		      // 遍历map，设置参数到list中
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
		          params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		      }
				// 创建form表单对象
		      UrlEncodedFormEntity formEntity = null;
			try {
				formEntity = new UrlEncodedFormEntity(params, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      
		      /*
		       * 	此处使用多个参数，并使用java对象来封装这些参数
		       */
//		      JSONObject paramJson = (JSONObject) JSON.toJSON(map);
//		      System.out.println(paramJson.toJSONString());
//		      StringEntity entity = new StringEntity(paramJson.toJSONString());
//		      entity.setContentEncoding(charset);
//		      entity.setContentType("application/json");
		      
		      
		        httpPost.setEntity(formEntity);
		      
		      
		      // 使用HttpClient发起请求，返回response
				HttpResponse response;
				try {
					response = httpClient.execute(httpPost);
					// 解析response封装返回对象httpResult
					System.out.println("返回结果："+EntityUtils.toString(response.getEntity())); 
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				} while (true);
//
//				long endtime = System.currentTimeMillis();
//				System.out.println("10000个请求时间："+((endtime-starttime)/1000)+"s");
			}
		}).start();
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		testTransOneParamObject();
	}
}
