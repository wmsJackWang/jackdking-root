//package org.jackdking.algorithm.insertthreechar;
//
//import org.jackdking.algorithm.basesort.Sort;
//import org.springframework.util.ObjectUtils;
//
//public class InsertThreeChar extends Sort{
//
//
//	public static void main(String[] args) {
//
//		String str = "hel  world   ,niha o shi jie ! @ ";
//
//		insertThreeChar(str);
//	}
//
//	public static void insertThreeChar(String str) {
//
//		if(ObjectUtils.isEmpty(str))
//			return ;
//		char[] chars = str.toCharArray();
//		int count =0;
//		for(char c : chars)
//			if(c==' ')
//				count++;
//		System.out.println("原来数组如下:");
//		printArray(chars);
//
//		char result[] = new char[count*2+chars.length+1];
//
//		int index = count*2+chars.length-1,indexc = chars.length-1;
//		for(;indexc >= 0 ; --indexc)
//			if(chars[indexc]!=' ')
//			{
//				result[index--]=chars[indexc];
//			}
//			else
//			{
//				result[index--]='0';
//				result[index--]='2';
//				result[index--]='%';
//			}
//
//		System.out.println("\n结果如下:");
//		printArray(result);
//
//	}
//
//}
