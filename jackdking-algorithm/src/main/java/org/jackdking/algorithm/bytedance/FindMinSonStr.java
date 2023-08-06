package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindMinStr
 * @Description TODO 给出两个字符串s和t、在s中找出最短的包含t中所有字符的连续子串
 * @Author jackdking
 * @Date 31/07/2023 8:56 下午
 * @Version 2.0
 **/
public class FindMinSonStr extends Sort {

  public static void main(String[] args) {


    String result = findMinSonStr("xyzaabbc", "xya");
    System.out.println(result);

  }

  private static String findMinSonStr(String s, String t) {
    int mark[] = new int[128];
    for (int i = 0 ; i <t.length(); i++) {
      mark[t.charAt(i)] ++;
    }

    int start = 0, end = 0, d = Integer.MAX_VALUE, count = t.length(), head= 0;
    for (; end < s.length() ; end ++) {

      if (mark[s.charAt(end)]-- > 0) {
          count--;
      }

      while (count==0) {
        if (end - start < d) {
          d = end - (head = start);
        }
        if (mark[s.charAt(start++)]++ == 0){
          count++;
        }

      }
    }
    return d == Integer.MAX_VALUE ? "" : s.substring(head, head+d+1);
  }
}
