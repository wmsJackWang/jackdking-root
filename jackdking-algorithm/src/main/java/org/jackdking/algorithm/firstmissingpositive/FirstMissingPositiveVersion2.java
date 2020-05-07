package org.jackdking.algorithm.firstmissingpositive;


//查询第一个缺失的正整数
//相对于第一个版本的解法，这个的时间复杂度是n，但是空间复杂度为n
//
public class FirstMissingPositiveVersion2 {
	
	public static void main(String[] args) {
		
		int[] nums = new int[]{4,-1,1,2,2,4,5,9,6,7,8,10,-1,11,13,12};
        int num = firstMissingPositive(nums);
        System.out.println(num==-1?"没有缺失的正整数":"缺失的正整数:"+num);
        
	}

	private static int firstMissingPositive(int[] nums) {
		// TODO Auto-generated method stub
		int size = nums.length ,k=-1;
		
		int a[] = new int[size]; 
		int result = -1;
		
		for(int i = 0 ; i < size ; ++i)
		{
			if(nums[i]>0&&nums[i]<=size)
			{
				a[nums[i]-1]=1;
				if(nums[i]>k)k = nums[i];
			}
		}
		
		for(int i = 0 ; i < size ; ++i)
		{
			if(a[i]==0)
			{
				result = i+1;
				break;
			}
		}
		
		if(k<result)
			result = -1;
		
		return result;
	}
	
	
	

}
