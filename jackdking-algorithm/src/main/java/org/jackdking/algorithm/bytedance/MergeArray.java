package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName MergeArray
 * @Description TODO
 * @Author jackdking
 * @Date 16/04/2023 9:09 下午
 * @Version 2.0
 **/
public class MergeArray extends Sort {

  public static void main(String[] args) {

    int[] arr = new int[]{13, 23, 24, 30, 32, 0, 0, 0};
    int[] arr2 = new int[]{12, 16, 44};
    mergeArray(arr, 5, arr2, 3);
    printArray("merge后的数组：", arr);


    arr = new int[]{};
    arr2 = new int[]{1};
    arr = mergeArray(arr, 0, arr2, 1);
    printArray("merge后的数组：", arr);
  }

  private static int[] mergeArray(int[] arr, int m, int[] arr2, int n) {

    if (arr.length == 0) {
      return arr2;
    }

    for (int i = 0; i < n; i++) {
      for (int j = m+i-1; j >= 0 ; j--){
        if (arr[j] > arr2[i]) {
          arr[j+1] = arr[j];
          if(j == 0) {
            arr[j] = arr2[i];
          }
        }else {
          arr[j+1] = arr2[i];
          break;
        }
      }
    }
    return arr;
  }

}
