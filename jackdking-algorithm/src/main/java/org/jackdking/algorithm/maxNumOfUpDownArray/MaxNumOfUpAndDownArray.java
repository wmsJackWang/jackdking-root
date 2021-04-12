package org.jackdking.algorithm.maxNumOfUpDownArray;

/*
 * 一个先递增后递减的数组，求
 */
public class MaxNumOfUpAndDownArray {

	
	static int[] array = {1,2,3,5,7,8,12,43,44,55,24,23,12,11,6,3,1};
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("最大数字索引值为："+getMaxNum(array));
		
	}

	private static int getMaxNum(int[] array) throws Exception {
		// TODO Auto-generated method stub

		int max = array.length-1 , min = 0 , mid , index;
		mid = (min+max)/2;

		while(mid>0&&mid<array.length-1) {
			if(array[mid]>array[mid-1]&&array[mid]>array[mid+1])
				return mid;
			else if(array[mid]>array[mid-1]) {
				min = mid+1;
				mid = (min+max)/2;
			}
			else if(array[mid]>array[mid+1]){
				max = mid-1;
				mid = (min+max)/2;
			}
		}
		return -1;
	}
	
}
