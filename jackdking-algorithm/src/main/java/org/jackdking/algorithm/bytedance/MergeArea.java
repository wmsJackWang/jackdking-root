package org.jackdking.algorithm.bytedance;

import net.minidev.json.JSONArray;
import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName MergeArea
 * @Description TODO
 * @Author jackdking
 * @Date 24/08/2023 9:55 上午
 * @Version 2.0
 **/
public class MergeArea extends Sort {

  public static void main(String[] args) {
    int [][] areas = {{1,3},{2,5},{8,10}};
    List<int[]>  result = mergeArea(areas);
    result.stream().forEach(System.out::println);

  }

  private static List<int[]> mergeArea(int[][] areas) {
    List<int[]> result = new ArrayList<>();
    for (int i = 0; i < areas.length-1 ; i++) {
      for (int j = i +1 ; j < areas.length ; j ++) {
        if ((areas[i][0] >=areas[j][0] && areas[i][0] <= areas[j][1])||(areas[i][1] <= areas[j][1]&& areas[i][1] >= areas[j][0])){
          int newleft = Math.min(areas[i][0], areas[j][0]);
          int newright = Math.max(areas[i][1], areas[j][1]);
          int [] newArea = {newleft, newright};
          result.add(newArea);
        }else {
          result.add(new int[]{areas[j][0], areas[j][1]});
        }
      }
    }
    return result;
  }
}
