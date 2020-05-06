package org.jackdking.algorithm.searchordermatrix;

public class searchOrderMatrix {
	//设置初始化的矩阵
	static int[][]matrix = new int[][] {{1,2,3,4},
										{3,5,6,7},
										{8,9,11,12}
							};

							
	//查找矩阵中是否有8
	public static void main(String[] args) {
		
		System.out.println("矩阵中是否存在这个数字:"+(isExist(matrix,12)?"存在":"不存在"));
		
	}
	
	public static boolean isExist(int[][]matrix,int x) {
		
		boolean isExist = false;
		int i =matrix[0].length , j = matrix.length;
		System.out.println("矩阵行长:"+i+",矩阵列长"+j);
		
		int hang=0, lie = i-1;
		
		
		while(hang <=j-1&&lie>=0)
		{
			if(matrix[hang][lie]==x)
			{
				isExist=true;
				break;
			}
			if(matrix[hang][lie]>x)
			{
				lie--;
				continue;
			}
			
			if(matrix[hang][lie]<x)
			{
				hang++;
				continue;
			}
			
			
		}
		
		return isExist;
		
	}
	
}
