package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.Map;

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

      result = buyStock20230326(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230326V2(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230328(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230415(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230417(arr);
      System.out.println("\nresult:" + result);

      printArray("原数组", arr);
      result = buyStock20230417V2(arr);
      System.out.println("\nresult:" + result);

      //极值法：最小阈值，最大收益
      printArray("原数组", arr);
      result = buyStock20230613(arr);
      System.out.println("\nresult:" + result);

      //动态回归
      printArray("原数组", arr);
      result = buyStock20230613V2(arr);
      System.out.println("\nresult:" + result);

      //动态回归
      printArray("原数组", arr);
      result = buyStock20230625(arr);
      System.out.println("\nresult:" + result);

      //动态回归
      printArray("原数组", arr);
      result = buyStock20230625V2(arr);
      System.out.println("\nresult:" + result);

      //动态回归
      printArray("原数组", arr);
      result = buyStock20230823(arr);
      System.out.println("\nresult:" + result);

  }

  private static int buyStock20230823(Integer[] arr) {
    int value = Integer.MIN_VALUE, dp[][] = new int[arr.length][2];
    dp[0][0] = 0;
    dp[0][1] = -arr[0];
    for (int i =1; i < arr.length ; i++) {
      dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + arr[i]);
      dp[i][1] = Math.max(-arr[i], dp[i-1][1]);
    }
    return dp[arr.length-1][0];
  }

  private static int buyStock20230625V2(Integer[] arr) {
    int min = Integer.MAX_VALUE, value = Integer.MIN_VALUE;
    for (int i = 0 ; i < arr.length ; i++) {
      if (arr[i] < min) {
        min = arr[i];
      } else {
        int k = arr[i] - min;
        if (k > value) {
          value = k;
        }
      }
    }
    return value;
  }

  private static int buyStock20230625(Integer[] arr) {
    int mark[][] = new int[arr.length][2];
    mark[0][0] = 0;
    mark[0][1] = -arr[0];

    for (int i = 1 ; i < arr.length; i++) {
      mark[i][0] = Math.max(mark[i-1][0], mark[i-1][1] + arr[i]);
      mark[i][1] = Math.max(-arr[i], mark[i-1][1]);
    }
    return mark[arr.length-1][0];
  }

  //只买卖一次
  private static int buyStock20230613V2(Integer[] arr) {
    int [][] result = new int[arr.length][2];

    result[0][0] = 0;
    result[0][1] = -arr[0];

    for (int index = 1 ; index < arr.length ;  index++) {
      result[index][0] = Math.max(result[index-1][0], result[index-1][1] + arr[index]);
      result[index][1] = Math.max(- arr[index], result[index-1][1]);
    }
    return result[arr.length-1][0];
  }

  private static int buyStock20230613(Integer[] arr) {

    int minVal = Integer.MAX_VALUE, maxProfit = Integer.MIN_VALUE;

    for (int i = 0 ; i < arr.length ; i++) {
      if (arr[i] < minVal) {
        minVal = arr[i];
      } else {
        int profit = arr[i] - minVal;
        if (profit > maxProfit) {
          maxProfit = profit;
        }
      }
    }
    return maxProfit;
  }

  private static int buyStock20230417V2(Integer[] arr) {
    int maxVal = Integer.MIN_VALUE;
    int min = arr[0];
    for (int i = 1; i < arr.length ; i++){
      if (arr[i] < min) {
        min = arr[i];
      }else {
        maxVal = Math.max(maxVal, arr[i] - min);
      }
    }
    return maxVal;
  }

  private static int buyStock20230417(Integer[] arr) {
      int mark[][] = new int[arr.length][2];
      mark[0][0] = 0;
      mark[0][1] = -arr[0];
      for (int i =1 ; i < arr.length ; i ++) {
        mark[i][0] = Math.max(mark[i-1][0], mark[i-1][1] + arr[i]);
        mark[i][1] = Math.max(mark[i-1][1], -arr[i]);
      }
      return mark[arr.length-1][0];
  }

  private static int buyStock20230415(Integer[] arr) {

      int dp[][] = new int[arr.length][2];
      dp[0][0] = 0;
      dp[0][1] = -arr[0];
      for (int i = 1;i < arr.length;i++) {
          dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + arr[i]);
          dp[i][1] = Math.max(dp[i-1][1],  - arr[i]);
      }
      return dp[arr.length -1][0];
    }




    private static int buyStock20230328(Integer[] arr) {
    int dp[][] = new int[arr.length][2];
    dp[0][0] = 0;
    dp[0][1] = -arr[0];
    for (int i = 1 ; i < arr.length ; i++){

      dp[i][0] = Math.max(dp[i-1][1] + arr[i], dp[i-1][0]);
      dp[i][1] = Math.max(-arr[i], dp[i-1][1]);
    }
    return dp[arr.length-1][0];
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

    private static int buyStock20230326V2(Integer[] arr) {
      int min = Integer.MAX_VALUE, maxVal = Integer.MIN_VALUE;
      for (int i = 0; i < arr.length; i++){
          if (arr[i] < min) {
              min = arr[i];
          }else {
              if (arr[i] - min > maxVal) {
                  maxVal = arr[i] - min;
              }
          }
      }
      return maxVal;
    }

    private static int buyStock20230326(Integer[] arr) {

        int dp[][] = new int[arr.length][2];//收益数组
        dp[0][0] = 0;
        dp[0][1] = -arr[0];
        for (int i = 1; i < arr.length; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + arr[i]);//当天没有股票，即当天卖掉或者前一天也没有
            dp[i][1] = Math.max(dp[i-1][1],  - arr[i]); // 当天有股票，即当天买或者前一天就有股票
        }
        return dp[arr.length-1][0];
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
