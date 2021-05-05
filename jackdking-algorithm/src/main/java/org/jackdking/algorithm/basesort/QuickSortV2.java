package org.jackdking.algorithm.basesort;

import java.util.Arrays;

public class QuickSortV2 {
	
	private static int[] arr = {12,23,22,100,-9,-1,10,100,77,234324343,645454,100,3223,34656,-12121};
	
	public static void main(String[] args) {
		
		System.out.println(Arrays.toString(arr));
		
		quickSort(arr, 0, arr.length-1);

		
		System.out.println(Arrays.toString(arr));
		
	}
	
	
	
	public static void quickSort(int [] array , int index1 , int index2){
		
		if(index1>=index2)
			return ;
		
		int p1 = index1,p2 = index2 ,k = array[index1];
	    // 基准数据
	    int key = array[p1];
	    while (p1 < p2) {
	        // 因为默认基准是从左边开始，所以从右边开始比较
	        // 当队尾的元素大于等于基准数据 时,就一直向前挪动 p2 指针
	        while (p1 < p2 && array[p2] >= key) {
	            p2--;
	        }
	        // 当找到比 array[p1] 小的时，就把后面的值 array[p2] 赋给它
	        if (p1 < p2) {
	            array[p1] = array[p2];
	        }
	        // 当队首元素小于等于基准数据 时,就一直向后挪动 p1 指针
	        while (p1 < p2 && array[p1] < key) {
	            p1++;
	        }
	        // 当找到比 array[p2] 大的时，就把前面的值 array[p1] 赋给它
	        if (p1 < p2) {
	            array[p2] = array[p1];
	        }
	    }
	    
	    array[p1] = key;
	    
	    quickSort(array, index1, p1-1);
	    quickSort(array, p1+1, index2);
		
		
	}

}
