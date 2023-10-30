package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindMedianSortedArrays
 * @Description TODO
 * @Author jackdking
 * @Date 23/08/2023 5:42 下午
 * @Version 2.0
 **/
public class FindMedianSortedArrays extends Sort {

  public static void main(String[] args) {
    int [] a = {1,4,7,11,23,26,57,78,99};
    int [] b = {2,5,6,18,22,27,59,77,180};
    int result = findMedianSortedArrays(a, b);
    System.out.println("result:"+result);
  }

  private static int findMedianSortedArrays(int[] a, int[] b) {
    int i = 0, j = 0, count = 0, val = 0;
    while (count!=a.length) {
      if (a[i]> b[j]) {
        val = b[j++];
      } else {
        val = a[i++];
      }
      count++;
    }
    return val;
  }
}
