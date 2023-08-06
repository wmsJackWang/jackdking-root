package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.springframework.util.StringUtils;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindStrEditDistance_v2
 * @Description TODO
 * @Author jackdking
 * @Date 02/08/2023 4:06 下午
 * @Version 2.0
 **/
public class FindStrEditDistance_v2 extends Sort {
  public static void main(String[] args) {


    int result = findStrEditDistance("abc", "adc", 5, 3, 100);
    System.out.println(result);

    result = findStrEditDistance("abc", "adc", 5, 3, 2);
    System.out.println(result);


  }

//  public int minEditCost (String str1, String str2, int ic, int dc, int rc) {
//    int m = str1.length();
//    int n = str2.length();
//    int[][] dp = new int[m + 1][n + 1];    // 行是str1，列是str2，题目要求把str1编辑成str2
//    for (int i = 1; i <= m; i++) dp[i][0] = dp[i - 1][0] + dc;    // str1 位置 i 字符 变成 str2 空字符 —— 需要delete
//    for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] + ic;    // str1 空字符 变成 str2 位置 i 字符 —— 需要insert
//    for (int i = 1; i <= m; i++) {
//      for (int j = 1; j <= n; j++) {
//        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
//          // 字符相同，无需花费cost
//          dp[i][j] = dp[i - 1][j - 1];
//        } else {
//          // 在insert，delete 和 replace中找到 cost 最小的一个
//          dp[i][j] = Math.min(dp[i - 1][j - 1] + rc, Math.min(dp[i - 1][j] + dc, dp[i][j - 1] + ic));
//        }
//      }
//    }
//    return dp[m][n];
//  }

  public static boolean isEmpty(String s) {
    if (s==null||s=="") {
      return true;
    }
    return false;
  }


  private static int findStrEditDistance(String s, String t, int a, int b, int c) {

    if (isEmpty(s) && isEmpty(t)) {
      return 0;
    }
    if (isEmpty(s)) {
      return t.length() * a;
    }
    if (isEmpty(t)) {
      return s.length() * b;
    }

    int mark[][] = new int[s.length()+1][t.length()+1];

    for (int i = 1;i<=s.length() ; i++) {
      mark[i][0] = i * b;
    }
    for (int i = 1;i<=t.length() ; i++) {
      mark[0][i] = i * a;
    }

    for (int i = 1; i <= s.length() ; i++) {
      for (int j = 1 ; j <= t.length() ; j++) {
        if (s.charAt(i-1) == t.charAt(j-1)) {
          mark[i][j] = mark[i-1][j-1];
        } else {
          int t1 = mark[i][j-1] + a;
          int t2 = mark[i-1][j] + b;
          int t3 = mark[i-1][j-1] + c;
          mark[i][j] = Math.min(t1, Math.min(t2, t3));
        }

      }
    }
    return mark[s.length()-1][t.length()-1];
  }
}
