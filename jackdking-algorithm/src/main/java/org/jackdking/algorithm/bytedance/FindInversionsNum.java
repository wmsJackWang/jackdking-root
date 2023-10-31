package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.Arrays;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindInversionsNum
 * @Description 找出逆序对数量 , 暴力搜素、合并排序
 * @Author jackdking
 * @Date 31/10/2023 4:13 下午
 * @Version 2.0
 **/
/*
 *
 * 1.在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。

示例 1:
输入: [7,5,6,4]
输出: 5
 */
public class FindInversionsNum extends Sort {

    static int sum = 0;

    public static void main(String[] args) {
        int arr[] = new int[]{7,5,6,4};
        mergeSort(arr, 0, arr.length-1);
        printArray("组合排序后数组 ", arr);
        System.out.println("逆序对数量：" + sum);

        printArray(Arrays.copyOfRange(arr, 0, 1));
    }

  private static void mergeSort(int[] arr, int begin, int end) {
    if (begin >= end) {
      return ;
    }
    int mid = (begin+end)/2;
    mergeSort(arr, begin, mid);
    mergeSort(arr, mid+1, end);
    merge(arr, begin, mid, end);
  }

  private static void merge(int[] arr, int begin, int mid, int end) {
      int i = 0, j = 0, index = begin;
      int left[] = Arrays.copyOfRange(arr, begin, mid+1);
      int right[] = Arrays.copyOfRange(arr, mid+1, end+1);
      while (i < left.length && j < right.length) {
        if (left[i] <= right[j]) {
          arr[index++] = left[i++];
        }else {
          arr[index++] = right[j++];
          sum += (mid+1) - (begin+i);
        }
      }
      while (i < left.length) {
        arr[index++] = left[i++];
      }
      while (j < right.length) {
        arr[index++] = right[j++];
      }
  }
}
