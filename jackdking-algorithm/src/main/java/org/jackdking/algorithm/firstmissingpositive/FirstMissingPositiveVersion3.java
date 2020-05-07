package org.jackdking.algorithm.firstmissingpositive;


//查询第一个缺失的正整数
//相对于第一个版本的解法，这个的时间复杂度是n，但是空间复杂度为n
//
public class FirstMissingPositiveVersion3 {
	
	public static void main(String[] args) {
		
		int[] nums = new int[]{7,-1,1,3,4,2,5,9,6,7,8,10,-1,11,13};
        int num = firstMissingPositive(nums);
        System.out.println(num==-1?"没有缺失的正整数":"缺失的正整数:"+num);
//		  byte x=1;
//		  System.out.println((byte)(x<<7|x>>1));
        
	}

	private static int firstMissingPositive(int[] nums) {
		// TODO Auto-generated method stub
		int size = nums.length ,k=-1;
		
//		int a[] = new int[size]; 
		byte[] a = new byte[size/8+1];
//		System.out.println(a[0]);
		int result = -1;
		
		for(int i = 0 ; i < size ; ++i)
		{
			if(nums[i]>0&&nums[i]<=size)
			{
//				a[nums[i]-1]=1;
				setBit(a,1,nums[i]);
				if(nums[i]>k)k = nums[i];
			}
		}
		
		for(int i = 0 ; i < size ; ++i)
		{
//			if(a[i]==0)
			if(isZero(a,i+1))
			{
				result = i+1;
				break;
			}
		}
		
		if(k<result)
			result = -1;
		
		return result;
	}

	private static boolean isZero(byte[] a, int index) {
		// TODO Auto-generated method stub
		int x = index/9,y= index%8;
		byte setToOne = 1;
		byte x1,x2;
		x1=(byte)(setToOne<<(8-y));
		x2=(byte)(setToOne>>y);
		setToOne=(byte)(x1|x2);
		if((a[x]&setToOne)==0)
			return true;
		
		return false;
	}

	private static void setBit(byte[] a, int value,int index) {
		// TODO Auto-generated method stub
		byte setToZero = 14;
		byte setToOne = 1;
		int x = index/9,y= index%8;
		
		if(value==1)
		{
			byte x1,x2;
			x1=(byte)(setToOne<<(8-y));
			x2=(byte)(setToOne>>y);
			setToOne=(byte)(x1|x2);
			a[x]=(byte)(a[x]|setToOne);
		}
		else {
			
		}
		
		
	}
	
	
	

}
