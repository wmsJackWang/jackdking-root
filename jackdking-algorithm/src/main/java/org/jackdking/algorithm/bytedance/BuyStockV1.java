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
      System.out.println("result:" + result);



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
