package org.jackdking.algorithm.fibonacci;

public class Fibonacci {
	
	
	public static void main(String[] args) {
		
		//非递归的方式
		System.out.println(fibonacci(30));
		
		//递归的方式
		System.out.println(fibonacciDiGui(30));
		
	}

	//非递归方式
	private static int  fibonacci(int n) {
		// TODO Auto-generated method stub
		
		int a = 0 , b =  0 , c = 0;
		
		if(n == 1||n==0)
			return 1;
		if(n == 2)
			return 2;
		
		a = 1;
		b = 2;
		
		for(int i = 3 ; i <= n ; ++i)
		{
			c = a + b;
			a = b;
			b = c;
		}
		return c;
		
	}
	

	//递归方式
	private static int  fibonacciDiGui(int n) {
		
		if(n == 1||n==0)
			return 1;
		if(n == 2)
			return 2;
		
		return fibonacciDiGui(n-1)+fibonacciDiGui(n-2);
	}
	

}
