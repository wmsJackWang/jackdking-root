package org.jackdking.algorithm.juzhenlujing;

public class JuZhenLuJing {
	
	//这个就是矩阵
	//问题就是判断矩阵是否存在路径，使得一条字符串与路径吻合
	//错误点：1)标记数组的索引与i,j的关系。2）注意最后判断是否到达路径最后，索引length[0]和str.length是不能直接看是否匹配的。(项目处理是将每次索引都提前加1)
	//注意：当该元素匹配时候，路径加1，当该元素所有周边元素不满足时候，路径减1，回溯到上一层。
	static char[][] martix = 	{	
							{'a','w','d','g','h','t','l','n'},
							{'a','w','d','g','h','t','l','n'},
							{'a','w','d','g','h','t','l','n'},
							{'a','w','d','g','h','t','l','n'},
							{'a','w','d','g','h','t','l','n'},
							{'a','w','d','g','h','t','l','n'}
			
						};
	//是否存在路径满足下面的字符串
//	static char[] str = "ddgghttnntthhhggddwwaa".toCharArray();
	static char[] str = "aaaaaawwwwwwddddddgggggghhhhhhttttln".toCharArray();
//	static char[] str = "awdghtttttn".toCharArray();
//	static char[] str = "aaaa".toCharArray();
	
	static boolean[] mark = new boolean[martix.length*martix[0].length];
	
	static int length[] = new int[1];
	
	public static void main(String[] args) {
		for(int i = 0 ; i < mark.length ; i++)
			mark[i] = false;
		
		length [0] = 0;
		
		for(int i = 0 ; i < martix.length ;i++)
			for(int j = 0 ; j < martix[0].length ; j++)
			{
				if(findRoad(i , j ))
					{
						System.out.println("存在路径");
						return ;
					}
			}

		System.out.println("不存在路径");
	}

	private static boolean findRoad(int i, int j) {
		// TODO Auto-generated method stub
		
		if(i<0||i>=martix.length||j<0||j>=martix[0].length||mark[i*martix[0].length+j]==true||martix[i][j]!=str[length[0]])
		{
			return false;
		}
		else{

			mark[i*martix.length+j]=true;
			length[0]++;
			System.out.println(length[0]);
			
			if(length[0]==str.length)
				return true;//结束
			if(findRoad(i-1, j)||findRoad(i+1, j)||findRoad(i, j-1)||findRoad(i, j+1))
			{
				return true;
			}else {
				
				length[0]--;
				mark[i*martix.length+j]=false;
				return false;
			}
		}
		
	}

}
