package org.jackdking.algorithm.huixingjuzhen;

import java.util.Scanner;

import ch.qos.logback.core.net.SyslogOutputStream;
public class byteheart {
	
    static int n;     
    static int sum;    //矩阵数字总个数
    static int flag = 1;
    static int[][] pattern;
	
	
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
    	huixingjuzhen();
//    	huixingjuzhen2();
    }
    
    private static void huixingjuzhen2() {
		// TODO Auto-generated method stub
    	
    	Scanner scan = new Scanner(System.in);
    	System.out.println("请输入数字: ");
    	n=scan.nextInt();
        pattern = new int[n][n];
    	if(n%2!=0)
    		pattern[(n-1)/2][(n-1)/2]=n*n;
    	sum = n*n;
		createPattern(0);
    	
		   // 输出图案
        for (int[] row : pattern) {
            for (int column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
		
	}

	private static void createPattern(int layer) {
		// TODO Auto-generated method stub
		
		for(int i =layer;i<n-1-layer;++i)
		{
			pattern[layer][i]=flag++;            
			if (flag > sum)
                return;
		}
		
		for(int i = layer;i<n-1-layer;++i)
		{
			pattern[i][n-1-layer]=flag++;            
			if (flag > sum)
                return;
			
		}
		
		for(int i = n-1-layer;i>layer;i--)
		{
			pattern[n-1-layer][i]=flag++;            
			if (flag > sum)
                return;
		}
		
		for(int i = n-1-layer;i>layer;i--)
		{
			pattern[i][layer]=flag++;            
			if (flag > sum)
                return;
		}
		
		createPattern(++layer);
		
	}

	public static void huixingjuzhen() {
    	
        System.out.println("请输入矩阵的行列数：");
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        pattern = new int[n][n];

        //预处理奇偶数的不同
        if(n%2==0)
            sum=n*n;
        else{
            sum=n*n-1;
            pattern[(n-1)/2][(n-1)/2]=n*n;
        }
        System.out.println("sum:"+sum);
        printPattern(0); //递归

        // 输出图案
        for (int[] row : pattern) {
            for (int column : row) {
                System.out.print(column + "\t");
            }
            System.out.println();
        }
    }
    
    /*
     * 打印图案功能，其中n为图案矩阵的行列数
     */
    static void printPattern(int layer) {

        // 上：赋值
        for (int i = layer; i < n - layer - 1; i++) {
            pattern[layer][i] = flag++;
            if (flag > sum)
                return;
        }
        // 右：赋值
        for (int i = layer; i < n - layer - 1; i++) {
            pattern[i][n - layer - 1] = flag++;
            if (flag > sum)
                return;
        }
        // 下：赋值
        for (int i = n - layer - 1; i > layer; i--) {
            pattern[n - layer - 1][i] = flag++;
            if (flag > sum)
                return;
        }
        // 左：赋值
        for (int i = n - layer - 1; i > layer; i--) {
            pattern[i][layer] = flag++;
            if (flag > sum)
                return;
        }

        printPattern(++layer);
    }
 
    
    
    public static void quicksort() {
    	
	   System.out.println("Hello World!");
       int array[] = new int[]{1,2,3,66,23,4,11,9,56,78,1,2,3,43,32,56,78,43,2237,3452,6,2,67};
       
       sort(array, 0, array.length-1);
       
       print(array);
    }
    
    public static void print(int [] array){
    	
    	for(int i : array)
    		System.out.print(i+" ");
    	System.out.println();
    }
    public static void sort(int [] array ,int i,int j){
        
    	if(i>=j)return;
    	
    	
        int p ,q ,k;
        p=i;
        q=i;
        for(;q<j;++q)
        {
            if(array[q]<array[j])
            {

            	if(p==q)
            	{
            		p++;
            		continue;
            	}
            	
            	k=array[q];
            	array[q]=array[p];
            	array[p]=k;
            	p++;
            }
           
        } 
        if(p!=q)
        {
        	k=array[q];
        	array[q]=array[p];
        	array[p]=k;
        }
        
        sort(array, i, p-1);
        sort(array, p+1, j);
    }
    
    
}