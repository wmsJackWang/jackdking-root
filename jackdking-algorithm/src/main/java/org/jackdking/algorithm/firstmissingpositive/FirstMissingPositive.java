package org.jackdking.algorithm.firstmissingpositive;

import java.util.Arrays;


/*
 *  先排序后查找
 */
public class FirstMissingPositive {

	public static void main(String[] args) {

        int[] nums = new int[]{3,4,-1,1,2,2,3,4,5,7,9};
        int num = firstMissingPositive(nums);
        System.out.println(num);
		
	}
	public static int firstMissingPositive(int[] nums) {
        int m = 1;
        int i = 0;
        if(nums.length == 0){
            return 1;
        }
        Arrays.sort(nums);
        //先找到第一个正整数
        while(i < nums.length){
            if(nums[i] < nums.length && nums[i] >= 1){
                int tmp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = tmp;
            }
            i++;
        }
        //从正整数数组中开始查找第一个缺失的正整数
        for(i = 0; i < nums.length;i++){
            if(nums[i] != i+1){
                return i+1;
            }
        }
        return nums.length+1;
    }
	
}
