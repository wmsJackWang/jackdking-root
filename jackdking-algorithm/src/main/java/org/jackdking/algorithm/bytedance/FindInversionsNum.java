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
        int arr[] = new int[]{7,5,6,4,5};
        mergeSort(arr, 0, arr.length-1);
        printArray("组合排序后数组 ", arr);
        System.out.println("逆序对数量：" + sum);

        sum = 0;
        arr = new int[]{7,5,6,4,2};
        mergeSort20231102(arr, 0, arr.length-1);
        printArray("组合排序后数组 ", arr);
        System.out.println("逆序对数量：" + sum);
        //问题总结：1. 左右数组，索引右边界搞混成mid了，应该是数组长度。 2. 左右数组的长度是跟拆分后的数组长度一样的。

    }

  private static void mergeSort20231102(int[] arr, int start, int end) {

      if (start>=end){
        return;
      }
      int mid = (start+end) /2;
      mergeSort20231102(arr, start, mid);
      mergeSort20231102(arr, mid+1, end);
      merge20231102(arr, start, mid, end);
  }

  private static void merge20231102(int[] arr, int start, int mid, int end) {
      int left[] = copyOfRange(arr, start, mid);
      int righ[] = copyOfRange(arr, mid+1, end);

      int l = 0, k = start, r = 0;
      while (l<=left.length-1 && r <= righ.length-1) {
        if (left[l] <= righ[r]) {
          arr[k++] = left[l++];
        } else {
          arr[k++] = righ[r++];
          sum += left.length-l;
        }
      }
      while (l <= left.length-1) {
        arr[k++] = left[l++];
      }
      while (r<= righ.length-1) {
        arr[k++] = righ[r++];
      }
  }

  private static int[] copyOfRange(int[] arr, int start, int end) {
      int newArr[] = new int[end-start+1];
      for (int i = start; i <= end; i++){
        newArr[i-start] = arr[i];
      }
      return newArr;
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
