package org.jackdking.redistemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	static String simple_pre = "SIMPLE";
	static String string_pre = "STRING";
	static String object_pre = "OBJECT";
 
    @Autowired
    @Qualifier("simpleRedisTemplate")
    private RedisTemplate<String, String> simpleRedisTemplate;
    

    @Autowired
    @Qualifier("stringSerializerRedisTemplate")
    private RedisTemplate<String, String> stringSerializerRedisTemplate;
    

    @Autowired
    @Qualifier("objectSerializerRedisTemplate")
    private RedisTemplate<String, String> objectSerializerRedisTemplate;
    
    
    
    @Test
    public void testSimpleRedisTemplate() {
    	
    	//hash数据结构 redis 的API测试
        Map<String, Object> properties = new HashMap<>();
        properties.put("123", "hello");
        properties.put("abc", 456);
    
        simpleRedisTemplate.opsForHash().putAll(simple_pre+"hash", properties);
    
        Map<Object, Object> ans = simpleRedisTemplate.opsForHash().entries(simple_pre+"hash");
        System.out.println("ans: " + ans);
    }
    @Test
    public void testStringSerializerRedisTemplate() {
    	
    	//hash数据结构 redis 的API
        Map<String, Object> properties = new HashMap<>();
        properties.put("123", "hello");
        properties.put("abc", 456);
    
        simpleRedisTemplate.opsForHash().putAll(string_pre+"hash", properties);
    
        Map<Object, Object> ans = simpleRedisTemplate.opsForHash().entries(string_pre+"hash");
        System.out.println("ans: " + ans);
    	
    }
    
    @Test
    public void testObjectSerializerRedisTemplate() {
    	
    	System.out.println("======获取hash的所有值================================================");
    	//hash数据结构 redis 的API
        Map<String, Object> properties = new HashMap<>();
        properties.put("123", "hello");
        properties.put("abc", 456);
        properties.put("countB",3.21);
        properties.put("countC",3.21);
        properties.put("countD",5);
        properties.put("countE",5);
        
    
        objectSerializerRedisTemplate.opsForHash().putAll(object_pre+"hash", properties);
        System.out.println("======获取hash的所有值==============================");
        Map<Object, Object> ans = objectSerializerRedisTemplate.opsForHash().entries(object_pre+"hash");
        System.out.println("ans: " + ans);
        
        System.out.println("======获取hash其中一个field的值======");
        Object result = objectSerializerRedisTemplate.opsForHash().get(object_pre+"hash", "abc");
        String abcResult = (String)result;
        System.out.println("abc:"+ abcResult);
        

        System.out.println("======对hash的countB,countC(3.21加减0.01)进行double 加值，减值 ======");
        objectSerializerRedisTemplate.opsForHash().increment(object_pre+"hash", "countB", 0.01);
        Object resultCountB = objectSerializerRedisTemplate.opsForHash().get(object_pre+"hash", "countB");
        Double abcResultCountB = Double.valueOf((String)resultCountB);
        System.out.println("加值后，countB :"+abcResultCountB);
        
        objectSerializerRedisTemplate.opsForHash().increment(object_pre+"hash", "countC", -0.01);
        Object resultCountC = objectSerializerRedisTemplate.opsForHash().get(object_pre+"hash", "countC");
        Double abcResultCountC = Double.valueOf((String)resultCountC);
        System.out.println("减值后，countC :"+abcResultCountC);
        

        System.out.println("======对hash的countD,countE(5加减10)进行double 加值，减值 ======");
        objectSerializerRedisTemplate.opsForHash().increment(object_pre+"hash", "countD", 10);
        Object resultCountD = objectSerializerRedisTemplate.opsForHash().get(object_pre+"hash", "countD");
        Double abcResultCountD = Double.valueOf((String)resultCountD);
        System.out.println("加值后，countD :"+abcResultCountD);
        
        objectSerializerRedisTemplate.opsForHash().increment(object_pre+"hash", "countE", -10);
        Object resultCountE = objectSerializerRedisTemplate.opsForHash().get(object_pre+"hash", "countE");
        Double abcResultCountE = Double.valueOf((String)resultCountE);
        System.out.println("减值后，countE :"+abcResultCountE);
        

        System.out.println("======对hash的field集合的hashkey进行批量获取 ======");
        Set<Object> keyList = objectSerializerRedisTemplate.opsForHash().keys(object_pre+"hash");
        Iterator<Object> keyIterator =  keyList.iterator();
        System.out.println("key的field集合:");
        while(keyIterator.hasNext())
        	System.out.println(keyIterator.next());
        
        System.out.println("======对hash的field集合的value进行批量获取 ======");
        Collection<Object> fieldList = new ArrayList<Object>();
        fieldList.addAll(properties.keySet());
        List<Object> hashValueList =  objectSerializerRedisTemplate.opsForHash().multiGet(object_pre+"hash", fieldList);
        Iterator<Object> iterator = hashValueList.iterator();
        System.out.println("hashValueList:");
        while(iterator.hasNext())
        	System.out.println((String)iterator.next());
        

        System.out.println("======对hash的field集合新添加k/v数据 ======");
        Map<Object, Object> resultBefore = objectSerializerRedisTemplate.opsForHash().entries(object_pre+"hash");
        System.out.println("添加前: " + resultBefore);
        
        objectSerializerRedisTemplate.opsForHash().put(object_pre+"hash", "field_add", "新添加字段");
        
        Map<Object, Object> resultAfter = objectSerializerRedisTemplate.opsForHash().entries(object_pre+"hash");
        System.out.println("添加后: " + resultAfter);
        
        System.out.println("======对hash的field 的hashkey的putifpresent操作======");
        boolean isSuccess = objectSerializerRedisTemplate.opsForHash().putIfAbsent(object_pre+"hash", "field_add", "新添加字段");
        System.out.println("新field添加是否成功："+isSuccess);
        
        
        System.out.println("======对hash的field 的hashkey的delete掉字段 field_add======");
        objectSerializerRedisTemplate.opsForHash().delete(object_pre+"hash", "field_add");
        Map<Object, Object> deleteAfter = objectSerializerRedisTemplate.opsForHash().entries(object_pre+"hash");
        System.out.println("删除后: " + deleteAfter);
        
        
        System.out.println("======对redis的list结构进行操作================================================");
        System.out.println("是否存在list队列: "+objectSerializerRedisTemplate.hasKey(object_pre+"list"));
        if(objectSerializerRedisTemplate.hasKey(object_pre+"list"))
        {
        	System.out.println("删除list"+objectSerializerRedisTemplate.delete(object_pre+"list"));
        	
        }
        
        ListOperations<String, String> listOperatrions = objectSerializerRedisTemplate.opsForList();
        System.out.println("======执行队列的元素push进去的操作");
        listOperatrions.leftPush(object_pre+"list", "list operation test");
        System.out.println("添加一个元素后队列大小："+listOperatrions.size(object_pre+"list"));
        System.out.println("======执行队列元素获取操作");
        System.out.println("取出一个队列元素:"+listOperatrions.rightPop(object_pre+"list"));
        new Thread(()->{
        	System.out.println("等待3s后将值放入到队列中");
        	try {
				TimeUnit.SECONDS.sleep(3);
				listOperatrions.leftPush(object_pre+"list", "list operation test");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }).start();
        System.out.println("开始阻塞等待队列的值。。。");
        System.out.println("阻塞被唤醒，获取队列中元素的值："+listOperatrions.rightPop(object_pre+"list", 0, TimeUnit.SECONDS));
        
        System.out.println("======一次放入3个 元素 test01,test02,test03");
        List<String> values = new ArrayList<String>();
        values.add("test01");
        values.add("test02");
        values.add("test03");
        listOperatrions.leftPushAll(object_pre+"list", values);
        System.out.println("执行完后，队列长度：" + listOperatrions.size(object_pre+"list"));
        
        System.out.println("======再放入2个元素test04,test05");
        listOperatrions.leftPushAll(object_pre+"list", "test04","test05");
        System.out.println("执行完后，队列长度：" + listOperatrions.size(object_pre+"list"));
        
        System.out.println();
        
    }
}