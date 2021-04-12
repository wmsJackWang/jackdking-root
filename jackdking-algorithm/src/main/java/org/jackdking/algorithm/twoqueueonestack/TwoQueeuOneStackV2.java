package org.jackdking.algorithm.twoqueueonestack;

import java.util.ArrayList;
import java.util.List;

/*
 * 用两个队列实现一个栈数据结构，先进先出规则。
 * 思路：在set的时候移动队列，先将元素放入空队列，再将另一个队列元素全部移动到该队列。
 */
public class TwoQueeuOneStackV2 {
	
	private List<String> q1 = new ArrayList<String>();

	private List<String> q2 = new ArrayList<String>();
	
	private List<String> dataQ = q1 ;
	
	public  void put(String e) {
		List<String> qu1 ;
		qu1 = dataQ == q1?q2:q1;
		qu1.add(e);//现将元素插入到空的队列中去。
		if(dataQ.size()>0) {
			
			for(int i = 0 ; i < dataQ.size() ;i++)
			{
				qu1.add(dataQ.get(i));
			}
			
			dataQ.clear();
			dataQ = qu1;
				
		}else {
			dataQ = qu1;
		}
	}
	
	public synchronized int size() {
		return dataQ.size();
	}
	
	
	public synchronized String get() {
		
		String e = null;
		if(dataQ.size()==0)
			return e;
		
		e = dataQ.get(dataQ.size()-1);
		dataQ.remove(dataQ.size()-1);
		return e;
	}

	
	public static void main(String[] args) {
		TwoQueeuOneStackV1 queeuOneStackV1 = new TwoQueeuOneStackV1();
		queeuOneStackV1.put("hello world");
		queeuOneStackV1.put("hello");
//		System.out.println(queeuOneStackV1.get());
		queeuOneStackV1.put("world");
		queeuOneStackV1.put("java");
		System.out.println(queeuOneStackV1.size());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		System.out.println(queeuOneStackV1.get());
		
	}
}
