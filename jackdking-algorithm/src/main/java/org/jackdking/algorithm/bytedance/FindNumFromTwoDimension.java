package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindNumFromTwoDimension
 * @Description TODO 二位数组中 查找是否有等于某个值的数字
 * @Author jackdking
 * @Date 31/07/2023 8:43 下午
 * @Version 2.0
 **/
public class FindNumFromTwoDimension extends Sort {


  public static void main(String[] args) {
    int [][]arr = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
    boolean result = findNumFromTwoDimension(15, arr);
    System.out.println(result);

    result = findNumFromTwoDimension20230811(15, arr);
    System.out.println(result);
  }

  private static boolean findNumFromTwoDimension20230811(int v, int[][] arr) {
    for (int i  =0;i<arr[0].length;i++) {
      if (arr[0][i] >v) break;
      for (int j =0;j< arr.length;j++) {
        if (arr[j][i] == v) {
          return true;
        } else if (arr[j][i] >v) {
          break;
        }
      }
    }
    return false;
  }

  private static boolean findNumFromTwoDimension(int k, int[][] arr) {
    for (int i = 0; i < arr[0].length ; i++) {
      if (arr[0][i] == k) {
        return true;
      } else if(arr[0][i] < k) {
        for (int j = 0 ; j < arr.length; j++) {
          if (arr[i][j] == k) {
            return true;
          } else if (arr[i][j] > k) {
            continue;
          }
        }
      } else {
          return false;
      }
    }
    return false;
  }

}
