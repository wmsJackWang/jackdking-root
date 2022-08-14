package org.jackdking.algorithm.basesort;

public class CountNumSort {
	
	
	//直接取数组的最大值  作为统计数组的长度
	public static void countNumSort(int array[]){
		
		int max = 0 ;
		for(int e:array)
			if(max<e)
				max = e;
		
		
		int[] count = new int [max];//错误点:统计数组的长度是原数组最大值
		
		for(int i = 0 ;i<array.length;++i)
			count[i]=0;
		
		for(int e:array)
		{
			count[e-1]++;
		}
		
		for(int k = 0;k < count.length ; ++k)//错误点二:这里的循环必须使用索引 自加的形式,因为我们需要count数组的索引来输出排序结果。
			for(int i=0;i<count[k];i++)
				System.out.print((k+1)+" ");
		
	}
	
	//取原数组中 最大值和最小值的相差值作为 统计数组的长度
	public static void countNumSortV2(int array[]) {
		
		int max = array[0] ,  min = array[0];
		for(int e : array)
		{
			if(max < e)
				max = e;
			if(min > e)
				min = e;
				
		}
		int length = max - min + 1 ;
		int count[] = new int [length];
		for(int i = 0 ; i < count.length;++i)
			count[i] = 0;
		
		for(int e:array)
			count[e-min]++;
		
		for(int i = 0 ; i < count.length;++i)
			for(int j = 0 ; j < count[i] ; ++j)
				System.out.print((i+min)+" ");
		
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		int array[]= {12,34,3,45,5,89,67,7,8,78,9,9};
		/*
		 * 第一种方法太浪费空间，如果数组存在特别大的值，那申请的数组空间也会非常的大。
		 *
		 */
		countNumSort(array);//3 5 7 8 9 9 12 34 45 67 78 89 
		System.out.println();

		/*
		 * 就算取数组中 最大值和最小值的差值作为 统计数组的长度，但是大数问题还是存在
		 */
		countNumSortV2(array);
		
	}

}
