package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindMinValInCycleArray
 * @Description TODO
 * @Author jackdking
 * @Date 31/10/2023 8:07 下午
 * @Version 2.0
 **/
public class FindMinValInCycleArray extends Sort {
  public static void main(String[] args) {
    int [] cycleArr = new int[]{ 4,4,4,4,1,2,3,4};
    int result = findMinValInCycleArray(cycleArr);
    System.out.println("循环数组中最小的值：" + result);
  }

  private static int findMinValInCycleArray(int[] cycleArr) {
    int left = 0, right = cycleArr.length-1, mid;
    while (left < right) {
      mid  = (left+right)/2;
      if (cycleArr[mid] > cycleArr[right]) {
        left = mid+1;
      } else if(cycleArr[mid] < cycleArr[right]) {
        right = mid;
      } else {
        right--;
      }
    }
    return cycleArr[left];
  }
}
