package org.jackdking.algorithm.frontk;

public class FrontKNum {
	
	
	public static int frontKNum(int[]array , int index1,int index2,int n) {
		
		
		if(index1>=index2)
			return array[index1];
		
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
		
		if(n-1==p1) return array[p1];
		if(n-1>p1) return frontKNum(array,p1+1,index2,n);
		if(n-1<p1) return frontKNum(array, index1, p1-1, n);
		return -1;
		
	}
	
	public static void main(String[] args) {
		
		//求第n大的数
		int n = 2;
		int array[] = new int[] {1,56,34,3,5,3,12,32,21,21,345,78,90};
		System.out.println(frontKNum(array, 0, array.length-1, array.length-n+1));
		//1 3 3 5 12 21 21 32 34 56 78 90 345 
	}

}
