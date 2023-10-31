package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FabinaciCalculate
 * @Description TODO
 * @Author jackdking
 * @Date 31/10/2023 7:08 下午
 * @Version 2.0
 **/
public class FabinaciCalculate extends Sort {
  public static void main(String[] args) {

    int result = fabinaciCalculate(10);
    System.out.println("斐波那契数:" + result);

    result = fabinaciCalculatePlus(10);
    System.out.println("斐波那契数:" + result);
  }

  private static int fabinaciCalculatePlus(int n) {
    int first = 0, second = 1, k = 0;
    for (int i = 2; i < n ; i++) {
      k = first + second;
      first = second;
      second = k;
    }
    return k;
  }

  private static int fabinaciCalculate(int n) {
    int dp[] = new int[n];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2 ; i < n; i++) {
      dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n-1];
  }
}
