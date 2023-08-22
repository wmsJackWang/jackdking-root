package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.junit.platform.commons.util.StringUtils;

import java.util.Scanner;

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

    result = findMinSonStr20230815("xyzaabbc", "abb");
    System.out.println(result);

    result = findMinSonStr20230816("xyzaabbc", "zabc");
    System.out.println(result);

    result = findMinSonStr20230820("xyzaabbc", "abb");
    System.out.println(result);

  }

  private static String findMinSonStr20230820(String s, String t) {

      int mark[]= new int[128];
      for (int i = 0 ; i < t.length() ; i++) {
        mark[t.charAt(i)] ++;
      }

      int head = 0 , ds = Integer.MAX_VALUE, start = 0, end = 0, count = t.length() ;
      for ( ; end < s.length() ; end ++) {
        if (mark[s.charAt(end)]-- > 0) {
          count--;
        }
        while (count==0) {
          if (ds > end - start) {
            head = start;
            ds = end - start;
          }
          if (mark[s.charAt(start++)]++==0) {
            count++;
          }
        }
      }
      return s.substring(head, head+ds+1);

  }

  private static String findMinSonStr20230816(String s, String t) {
    if (StringUtils.isBlank(s) || StringUtils.isBlank(t)) {
      return null;
    }
    int mark[] = new int[128];
    for (int i = 0 ; i < t.length() ; i++){
      mark[t.charAt(i)] ++;
    }
    int count = t.length(), head = 0, start = 0, end = 0, windowSize = Integer.MAX_VALUE;
    for (; end < s.length() ; end++) {
      if (mark[s.charAt(end)]-- > 0) {
        count--;
      }
      while (count==0) {
        if (windowSize > (end - start)) {
          windowSize = end - start;
          head = start;
        }
        if (mark[s.charAt(start++)]++ == 0) {
          count++;
        }

      }
    }
    return windowSize == Integer.MAX_VALUE ? "" : s.substring(head, head+windowSize +1);

  }

  private static String findMinSonStr20230815(String s, String t) {

    int mark[] = new int[128];
    for (int i = 0 ; i < t.length() ; i++) {
      mark[t.charAt(i)]++;
    }
    int start = 0, end=0, minWindow = Integer.MAX_VALUE, counter = t.length(), head = 0;
    for (; end < s.length() ; end++) {

      if (mark[s.charAt(end)]-->0) {
        counter--;
      }
      while (counter == 0) {
        if (minWindow > end - start) {
          minWindow = end - start;
          head = start;
        }

        if (mark[s.charAt(start++)]++ == 0) {
          counter++;
        }
      }
    }

    return minWindow == Integer.MAX_VALUE ? "" : s.substring(head, head+minWindow+1);
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
