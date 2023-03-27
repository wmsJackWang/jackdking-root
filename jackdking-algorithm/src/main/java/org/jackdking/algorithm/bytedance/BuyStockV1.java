package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BuyStockV1
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 5:11 下午
 * @Version 2.0
 **/
public class BuyStockV1 extends Sort {
  public static void main(String[] args) {

      Integer[] arr = createArray(10, 20);
      printArray("原数组", arr);
      int result = buyStock(arr);
      System.out.println("\nresult:" + result);

//      arr = createArray(10, 20);
      printArray("原数组", arr);
      result = buyStock20230319(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230323(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230323V2(arr);
      System.out.println("\nresult:" + result);

  }

  //动态规划 只买卖一次
  private static int buyStock20230323V2(Integer[] arr) {
      int [][]dp = new int[arr.length][2];
      dp[0][0] = 0;
      dp[0][1] = -arr[0];
      for (int i = 1; i < arr.length; i ++) {
        dp[i][0] =  Math.max(dp[i-1][0], dp[i-1][1] + arr[i]);
        dp[i][1] = Math.max(dp[i-1][1], - arr[i]);
      }
      return dp[arr.length-1][0];
  }

  //1.最小值是0，即都没有操作买卖
  //2.保留历史最小值和最大获利
  private static int buyStock20230323(Integer[] arr) {
      int min = Integer.MAX_VALUE;
      int res = 0;
      int cur;
      for (Integer ele : arr){
        if (min > ele) {
          min = ele;
        }else {
          cur = ele - min;
          if (cur > res) {
            res = cur;
          }
        }
      }
      return res;
  }

  private static int buyStock20230319(Integer[] arr) {
      int [][] profit = new int[arr.length][2];
      profit[0][0] = 0;
      profit[0][1] = -arr[0];

      for (int i = 1 ; i < arr.length ; i ++) {
          profit[i][0] = Math.max(profit[i-1][0], profit[i-1][1]+ arr[i]);
          profit[i][1] = Math.max(-arr[i], profit[i-1][1]);
      }
      return profit[arr.length-1][0];
    }

    private static int buyStock(Integer[] arr) {

    int minVal = Integer.MAX_VALUE;
    int res = 0;
    for (int i = 0 ; i < arr.length ; i++) {
      if (arr[i] < minVal) {
        minVal = arr[i];
      }else if(arr[i] - minVal > res){
        res = arr[i] - minVal;
      }
    }
    return res;
  }
}
