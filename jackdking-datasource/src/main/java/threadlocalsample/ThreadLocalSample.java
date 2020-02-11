package threadlocalsample;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalSample {
	public static ThreadLocal<Map<String,String>> data = new ThreadLocal<Map<String,String>>();
	
}

