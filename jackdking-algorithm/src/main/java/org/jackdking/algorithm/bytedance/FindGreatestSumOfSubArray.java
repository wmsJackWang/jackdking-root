package org.jackdking.algorithm.bytedance;

/*
 * 描述
输入一个长度为n的整型数组array，数组中的一个或连续多个整数组成一个子数组，子数组最小长度为1。求所有子数组的和的最大值。
数据范围:
1 <= n <= 2\times10^51<=n<=2×10
5

-100 <= a[i] <= 100−100<=a[i]<=100

要求:时间复杂度为 O(n)O(n)，空间复杂度为 O(n)O(n)
进阶:时间复杂度为 O(n)O(n)，空间复杂度为 O(1)O(1)
示例1
输入：
[1,-2,3,10,-4,7,2,-5]
复制
返回值：
18
复制
说明：
经分析可知，输入数组的子数组[3,10,-4,7,2]可以求得最大和为18
*
* https://www.nowcoder.com/practice/459bd355da1549fa8a49e350bf3df484?tpId=196&tqId=37130&rp=1&ru=/exam/company&qru=/exam/company&sourceUrl=%2Fexam%2Fcompany&difficulty=undefined&judgeStatus=undefined&tags=&title=
 */
/*
 * 回归解题思路
 * 回归数组dp[i]的意义：所有包含第i个元素连续子数组中，sum最大值。 转移函数如下：dp[i] = Math.max(dp[i], dp[i-1] + dp[i])
 */
public class FindGreatestSumOfSubArray {
  public static void main(String[] args) {
      int[] array = {1, -2, 3, 10, -4, 7, 2, -5};
      int result = findGreatestSumOfSubArray(array);
      System.out.println("result:" + result);

      result = findGreatestSumOfSubArray20230412(array);
      System.out.println("result:" + result);

      result = findGreatestSumOfSubArray20230415(array);
      System.out.println("result:" + result);

      result = findGreatestSumOfSubArray20230417(array);
      System.out.println("result:" + result);

      result = findGreatestSumOfSubArray20231031(array);
      System.out.println("result:" + result);
  }

  private static int findGreatestSumOfSubArray20231031(int[] array) {
      int max = Integer.MIN_VALUE;
      int dp[] = new int[array.length];
      dp[0] = array[0];
      for (int i = 1 ; i < array.length ; i ++) {
        dp[i] = Math.max(dp[i], dp[i-1] + array[i]);
        max = Math.max(dp[i], max);
      }
      return max;
  }

  private static int findGreatestSumOfSubArray20230417(int[] array) {
      int maxVal = array[0];
      int mark[] = new int[array.length];
      mark[0] = array[0];
      for (int i =1; i < array.length ; i++) {
        mark[i] = Math.max(mark[i-1] + array[i], array[i]);
        maxVal = Math.max(maxVal, mark[i]);
      }
      return maxVal;
  }



  private static int findGreatestSumOfSubArray20230415(int[] array) {

      int[] dp = new int[array.length];
      dp[0] = array[0];
      int maxNum =array[0];
      for (int i=1;i < array.length;i++){
          dp[i] = Math.max(dp[i-1]+array[i], array[i]);
          maxNum = Math.max(dp[i], maxNum);
      }
      return maxNum;
    }


    private static int findGreatestSumOfSubArray20230412(int[] array) {
      int dp[] = new int[array.length];
      dp[0] = array[0];
      int maxNum = 0;
      for (int i = 1;i < array.length ;i++){
        dp[i] = Math.max(dp[i-1] + array[i], array[i]);
        maxNum = Math.max(dp[i], maxNum);
      }
      return maxNum;

  }

  private static int findGreatestSumOfSubArray(int[] array) {

        int dp[] = new int[array.length];
        dp[0] = array[0];
        int maxNum = array[0];
        for (int i = 1; i < array.length; i++) {
            dp[i] = Math.max(dp[i-1]+array[i], array[i]);
            maxNum = Math.max(dp[i], maxNum);
        }
        return maxNum;
    }
}
