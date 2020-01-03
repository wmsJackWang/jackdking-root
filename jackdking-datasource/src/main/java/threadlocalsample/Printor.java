package threadlocalsample;

import java.util.Map;

public class Printor {
	public static void print() {
		 
		Map<String, String> result = ThreadLocalSample.data.get();
		System.out.println("结果为："+result.get("key"));
	}
}
