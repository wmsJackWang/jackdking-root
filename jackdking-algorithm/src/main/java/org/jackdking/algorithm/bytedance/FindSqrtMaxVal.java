package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindSqrtMaxVal
 * @Description TODO
 * @Author jackdking
 * @Date 31/07/2023 10:08 下午
 * @Version 2.0
 **/
public class FindSqrtMaxVal extends Sort {

  public static void main(String[] args) {

    int result = findSqrtMaxVal(2143195649);
    System.out.println(result);
  }

  private static int findSqrtMaxVal(int k) {
    int left = 0 , right = k/2, mid = 0;
    long temp = 0;
    while (left<=right) {
      mid = (left+right)/2;
      temp = (long)mid*mid;
      if (temp > k) {
        right = mid -1;
      } else if (temp < k) {
        left = mid + 1;
      }
    }
    return left-1;
  }
}
