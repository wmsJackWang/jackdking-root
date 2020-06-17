package org.jackdking.algorithm.basesort;

public class MergeSort extends Sort{

	static int array[]= {12,34,3,45,5,89,67,7,8,78,9,9};
	static int temp [] = new int[array.length];
	
	public static void main(String[] args) {

		mergeSort(array,0,array.length-1);
		printArray(array);
		
	}

	public static void mergeSort(int [] array , int index1,int index2) {
		
		
		if(index1>=index2)
			return;
		
		int middle = (index1+index2)/2;
		mergeSort(array, index1, middle);
		mergeSort(array, middle+1, index2);
		merge(array,index1,middle,index2);
		
		
		
	}
	
	public static void merge(int[] array2, int index1, int middle, int index2) {
		
		int x = index1,y = middle+1,z=index1;
		while(x<=middle&&y<=index2)
		{
			if(array2[x]>array2[y])//将大得值放到数组的前面，最后得到从大到小的数组。
				temp[z++]=array[x++];
			else
				temp[z++]=array[y++];
		}
		while(x<=middle)
		{
			temp[z++]=array[x++];
		}
		while(y<=index2)
		{
			temp[z++]=array[y++];
		}
		z = index1;
		
		while(z<=index2)
		{
			array2[z]=temp[z];
			++z;
		}
		
	}
}
