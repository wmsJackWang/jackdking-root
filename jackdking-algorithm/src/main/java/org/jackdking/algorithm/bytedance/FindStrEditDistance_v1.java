package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.junit.platform.commons.util.StringUtils;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindStrEditDistance
 * @Description TODO
 * @Author jackdking
 * @Date 02/08/2023 3:42 下午
 * @Version 2.0
 **/
public class FindStrEditDistance_v1 extends Sort {
  public static void main(String[] args) {

    int result = findStrEditDistance("hello", "hellk");
    System.out.println(result);

    result = findStrEditDistance0804("hello", "hellk");
    System.out.println(result);
  }


  public static boolean isEmpty(String s) {
    if (s == null || s == "") {
      return true;
    }
    return false;
  }
  private static int findStrEditDistance0804(String s, String t) {

    if (isEmpty(s) && isEmpty(t)) {
      return 0;
    }

    if (isEmpty(s)) {
      return t.length();
    }

    if (isEmpty(t)) {
      return s.length();
    }

    int [][]mark = new int[s.length()+1][t.length()+1];

    for (int i =1; i <=s.length() ; i++) {
      mark[i][0] = i;
    }
    for (int i =1; i <=t.length() ; i++) {
      mark[0][i] = i;
    }

    for (int i = 1 ; i <= s.length();i++) {
      for (int j = 1 ; j <= t.length() ; j ++){
        if (s.charAt(i-1) == t.charAt(j-1)) {
          mark[i][j] = mark[i-1][j-1];
        }else {
          mark[i][j] = Math.min(Math.min(mark[i][j-1], mark[i-1][j]), mark[i-1][j-1]) +1;
        }
      }
    }
    return mark[s.length()][t.length()];
  }

  private static int findStrEditDistance(String s, String d) {
    if (StringUtils.isBlank(s) && StringUtils.isBlank(d)) {
      return 0;
    }

    if (StringUtils.isBlank(s)) {
      return d.length();
    }

    if (StringUtils.isBlank(d)) {
      return s.length();
    }

    int [][]mark = new int[s.length()][d.length()];

    for (int i = 0 ; i < s.length(); i++) {
      for (int j = 0 ; j < d.length() ; j++) {
        if (i == 0) {
          mark[i][j] = j;
          continue;
        }
        if (j == 0) {
          mark[i][j] = i;
          continue;
        }
        if (s.charAt(i) == d.charAt(j)) {
          mark[i][j] = mark[i-1][j-1];
        } else {
          mark[i][j] = Math.min(Math.min(mark[i-1][j], mark[i][j-1]), mark[i-1][j-1]) + 1;
        }
      }
    }

    return mark[s.length()-1][d.length()-1];
  }
}
