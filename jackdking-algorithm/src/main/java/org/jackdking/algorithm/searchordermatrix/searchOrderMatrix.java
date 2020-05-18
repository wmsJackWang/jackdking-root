package org.jackdking.algorithm.searchordermatrix;

public class searchOrderMatrix {
	//设置初始化的矩阵
	static int[][]matrix = new int[][] {{1,2,3,4,12},
										{3,5,6,7,13},
										{8,9,11,12,33},
										{10,12,16,17,45}
							};

							
	//查找矩阵中是否有8
	public static void main(String[] args) {
		
		int x =55;
		System.out.println("矩阵中是否存在数字"+x+":"+(isExist(matrix,x)?"存在":"不存在"));
		
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
