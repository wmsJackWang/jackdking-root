package org.jackdking.algorithm.pukeorder;

import java.util.ArrayList;
import java.util.Arrays;

public class PuKeOrderTest {
	public static void main(String[] args) {
	
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7"));
		ArrayList<String> s2 = new ArrayList<String>();
		
		for(int i=s1.size()-1;i>=0;i--)
		{
			String temp = s1.get(i);
			if(s2!=null&&s2.size()>0)
			{
				String s = s2.get(s2.size()-1);
				s2.remove(s2.size()-1);
				s2.add(0, s);
			}
			
			s2.add(0, temp);
		}
		
		
		System.out.println(s2);
		
		
	}
}
