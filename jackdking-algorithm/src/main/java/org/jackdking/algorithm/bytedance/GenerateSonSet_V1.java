package org.jackdking.algorithm.bytedance;

import com.google.common.collect.Lists;
import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GenerateSonSet_V1
 * @Description TODO
 * @Author jackdking
 * @Date 31/07/2023 5:34 下午
 * @Version 2.0
 **/
public class GenerateSonSet_V1 extends Sort {

  public static void main(String[] args) {

    List<List<Integer>> result = new ArrayList<>();
    Integer[] arr = new Integer[]{1,2,3};

    for (int i = 0 ; i <= arr.length ; i++) {
      generateSonset(result, Lists.newArrayList(), arr, 0, i);
    }
    System.out.println(result);


    result = new ArrayList<>();
    arr = new Integer[]{1,2,3};
    for (int i = 0; i <= arr.length; i++) {
      generateSonset20230801(result, Lists.newArrayList(), arr, 0, i);
    }
    System.out.println(result);

    result = new ArrayList<>();
    arr = new Integer[]{1,2,3};
    for (int i = 0; i <= arr.length; i++) {
      // size逐渐递减
      generateSonset20230823(result, Lists.newArrayList(), arr, 0, i);
    }
    System.out.println(result);

  }

  private static void generateSonset20230823(List<List<Integer>> result, ArrayList<Integer> newArrayList, Integer[] arr, int n, int m) {

    if (m == 0) {
      result.add(newArrayList);
      return;
    }

    if (n < arr.length) {
      ArrayList<Integer> tempList = Lists.newArrayList(newArrayList);
      tempList.add(arr[n]);
      generateSonset20230823(result, tempList, arr, n +1, m-1);
      generateSonset20230823(result, newArrayList, arr, n +1, m);
    }

  }

  private static void generateSonset20230801(List<List<Integer>> result, ArrayList<Integer> newArrayList, Integer[] arr, int start, int size) {
    if (size == 0) {
      result.add(newArrayList);
      return;
    }

    if (start >= arr.length) {
      return;
    }
    ArrayList<Integer> list = Lists.newArrayList(newArrayList);
    list.add(arr[start]);
    generateSonset(result, list, arr, start+1, size-1);

    generateSonset(result, newArrayList, arr, start+1, size);

  }

  private static void generateSonset(List<List<Integer>> result, ArrayList<Integer> newArrayList, Integer[] arr, int start, int size) {
    if (size==0) {
      result.add(newArrayList);
      return;
    }

    if (start < arr.length) {
      ArrayList<Integer> list = Lists.newArrayList(newArrayList);
      list.add(arr[start]);
      generateSonset(result, list, arr, start+1, size-1);
      generateSonset(result, newArrayList, arr, start+1, size);
    }
  }
}
