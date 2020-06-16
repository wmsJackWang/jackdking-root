package org.jackdking.algorithm.basesort;

public class QuickSort {
	
	public static void  quickSort(int[] array , int index1 , int index2) {
		
		if(index1>=index2)
			return ;
		
		int p1 , p2 , k;
		p1 = p2 = index1;
		for(; p2 < index2 ;++p2)
		{
			if(array[p2]<array[index2])
			{
				if(p2!=p1)
				{
					k = array[p1];
					array[p1] = array[p2];
					array[p2] = k;
				}
				p1++;
			}
		}
		
		if(p1!=index2)
		{
			k = array[index2];
			array[index2] = array[p1];
			array[p1] = k;	
		}
		
		quickSort(array, index1, p1-1);
		quickSort(array, p1+1, index2);
		
	}
	
	public static void printArray(int []array){
		for(int i:array)
			System.out.print(i+" ");
	}

	
	public static void main(String[] args) {
		
		int array[] = new int[] {1,56,34,3,5,3,12,32,21,21,345,78,90};
		quickSort(array, 0, array.length-1);
		printArray(array);
		
	}
	
}
