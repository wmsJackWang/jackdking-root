package threadlocalsample;

import java.util.HashMap;
import java.util.Map;

public class Sample {
	
	public static void main(String[] args) {
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("key", "value-test");
		ThreadLocalSample.data.set(result);
		
		//result数据没有作为print函数的参数传递进去，但是却打印出来了。
		Printor.print();
	}
}
