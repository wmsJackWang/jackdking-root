package org.jackdking.algorithm.twoqueueonestack;

import java.util.ArrayList;
import java.util.List;

/*
 * 用两个队列实现一个栈数据结构，先进先出规则。
 * 思路：在get的时候移动队列数据。
 */
public class TwoQueeuOneStackV1 {
	
	private List<String> q1 = new ArrayList<String>();

	private List<String> q2 = new ArrayList<String>();
	
	private List<String> dataQ = q1 ;
	
	public  void put(String e) {
		
		dataQ.add(e);
	}
	
	public synchronized int size() {
		return dataQ.size();
	}
	
	public synchronized String get() {
		
		String e = null;
		List<String> qu1 ;
		qu1 = dataQ==q1?q2:q1;
		int i = 0;
		if(dataQ.size()>1) {//数据队列数量大于1，则进行数据转移处理
			for(;i<dataQ.size()-1;i++){
				qu1.add(dataQ.get(i));
			}
		}else {
			//没有元素，直接返回null
//			return null;
		}
		
		if(dataQ.size()!=0)
			e = dataQ.get(i);
		dataQ.clear();
		dataQ = qu1;
		
		return e;
	}	

	
	public static void main(String[] args) {
		TwoQueeuOneStackV1 queeuOneStackV1 = new TwoQueeuOneStackV1();
		queeuOneStackV1.put("hello world");
		queeuOneStackV1.put("hello");
		System.out.println(queeuOneStackV1.get());
		queeuOneStackV1.put("world");
		queeuOneStackV1.put("java");
		System.out.println(queeuOneStackV1.size());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		
	}
}
