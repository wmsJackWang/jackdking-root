package org.jackdking.algorithm.countbitone;

import org.jackdking.algorithm.basesort.Sort;

//求一个整数的二进制 数据中1的个数
public class BitOneCount extends Sort {


	public static void main(String[] args) {
		System.out.println(getBitOneCount(-15));
		System.out.println(getBitOneCount2(-15));
		System.out.println(getBitOneCount(15));
		System.out.println(getBitOneCount3(-15));


		System.out.println(getBitOneCount230317(15));
		System.out.println(getBitOneCount230317(-15));
	}

  	private static int getBitOneCount230317(int k) {
	  	if (k==0) {
	    	return 0;
    	}

		int count = 0;
		if (k > 0){
			while (k > 0) {
				if (k%2 == 1) {
				  count++;
				}
				k/=2;
			}
		}else {
			int m = k *-1;
			int [] bma = new int[32];
			//初始化补码的数组
			for (int i = 0 ; i < bma.length ; i++) {
			  bma[i] = 1;
			}
			for (int i = bma.length - 1 ; i > 0 && m > 0 ; i--) {
			  if (m%2==1) {
				bma[i] = 0;
			}
			  m/=2;
			}
			int sum = 1;
			count +=1;//i=0属于符号，因此不参加求补码的运算中去
			for (int i = bma.length - 1 ; i > 0 ; i --) {
			  if (bma[i] + sum == 2) {
				sum = 1;
				bma[i] = 0;
			}
			  if (bma[i] + sum == 1) {
				count ++;
				sum = 0;
			}
			}

			printArray(bma);
    }
    return count;
  }

  	//使用java特有的api
	private static int getBitOneCount(int i) {
		// TODO Auto-generated method stub

		String result =  Integer.toBinaryString(i);
		char [] arr = result.toCharArray();
		int count = 0 ;
		for(char ch : arr)
			if(ch=='1')count++;

		return count;
	}

	//使用java特有的api
	private static int getBitOneCount2(int i) {
		return Integer.bitCount(i);
	}

	//不使用java的api
	private static int getBitOneCount3(int k) {

		int count = 0;

		if(k==0)
			return 0;
		if(k>0)
			while(k>0)
			{
				if(k%2==1)count++;
				k=k/2;
			}
		else {

			int [] arr = new int[32];
			for(int i=0;i< arr.length;++i)
				arr[i]=1;

			count++;//负数 天生就有一个为1的符号位
			int index=arr.length-1;
			int sum=1;
			while(k!=0)
			{
				if(k%2==0)
					arr[index--]=1;
				else
					arr[index--]=0;
				k=k/2;
			}
			printArray(arr);

			for(int i = arr.length-1; i > 0;--i)
			{
				if(arr[i]+sum==2)
				{
					arr[i] = 0;
					sum  = 1;
				}
				else if(arr[i]+sum==1)
				{
					arr[i] = 1;
					sum = 0;
					count++;
				}
			}

		}

		return count;
	}


}
