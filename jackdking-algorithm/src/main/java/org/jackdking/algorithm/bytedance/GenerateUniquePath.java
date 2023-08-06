package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GenerateUniquePath
 * @Description TODO 不同路径的数目
 * @Author jackdking
 * @Date 31/07/2023 11:28 下午
 * @Version 2.0
 **/
public class GenerateUniquePath extends Sort {

  public static void main(String[] args) {
    int result = generateUniquePath(2,2);
    System.out.println(result);

    result = generateUniquePath20230802(6,5);
    System.out.println(result);

  }

  private static int generateUniquePath20230802(int m, int n) {

    if(m==1) {
      return 1;
    }

    if (n==1) {
      return 1;
    }

    return generateUniquePath(m-1, n)+generateUniquePath(m, n-1);

  }

  private static int generateUniquePath(int m, int n) {

    if (m == 1) return 1;
    if (n == 1) return 1;
    return generateUniquePath(m-1, n) + generateUniquePath(m, n-1);
  }
}
