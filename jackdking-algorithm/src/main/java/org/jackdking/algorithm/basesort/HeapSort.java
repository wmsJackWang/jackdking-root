package org.jackdking.algorithm.basesort;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;

/*
 * 堆排序
 */
public class HeapSort extends Sort{

	public static void main(String[] args) {
		
		int array[]= {12,34,3,45,5,89,67,7,8,78,9,9};
//		HeapSort(array);
		HeapSort2(array);
		printArray(array);
		
	}
	
	
	
	/*
	 * 我这个算法的时间复杂度是n的平方，因为我这个进行比较是基于子节点与父节点进行依次比较的思想
	 */
	public static void HeapSort(int [] array) {
		
		int k = 0;
		for(int index1 = array.length-1 ; index1 > 0 ; index1--)
		{
			for(int index2 = index1 ;index2>0 ; index2--)
			{
				if(array[(index2-1)/2]<array[index2])
				{
					k = array[index2];
					array[index2] = array[(index2-1)/2];
					array[(index2-1)/2] = k;
				}
			}
			
			k = array[0];
			array[0] = array[index1];
			array[index1] = k;
			
		}
		
	}
	
	/*
	 * 堆排序，基于父节点循环的方式，时间复杂度是 n*log2n.
	 */
	public static void HeapSort2(int [] array) {
		

		int k = 0 , left , right;
		for(int index1 = array.length-1 ; index1 > 0 ; index1--)
		{
			for(int index2 = (index1-1)/2 ; index2 >=0 ; index2--)
			{
				left = (index2+1)*2-1;
				right = (index2+1)*2;
				
				int index = left;
				if(right<=index1)//防止一开始的时候右节点不存在，导致数组越界错误。
					index =array[left]>array[right]?left:right;
				int x = array[index]>array[index2]?index:index2;
				if(x!=index2)
				{
					k = array[index2];
					array[index2]= array[x];
					array[x]  = k;
				}
			}
			k = array[0];
			array[0] = array[index1];
			array[index1] = k;
		}
	}
	
	
}
