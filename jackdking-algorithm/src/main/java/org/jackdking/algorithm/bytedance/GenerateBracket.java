package org.jackdking.algorithm.bytedance;

import net.minidev.json.JSONValue;
import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GenerateBracket
 * @Description TODO 生成n对括号的所有结果
 * @Author jackdking
 * @Date 31/07/2023 2:54 下午
 * @Version 2.0
 **/
public class GenerateBracket  extends Sort {

  public static void main(String[] args) {

    ArrayList<String> result = new ArrayList<>();
    generateBracket(result, 0, 0, "", 3);
    System.out.println(result);

    result = new ArrayList<>();
    generateBracket20230808(result, 0, 0, "", 3);
    System.out.println(result);

    result = new ArrayList<>();
    generateBracket20230811(result, 0, 0, "", 3);
    System.out.println(result);

    result = new ArrayList<>();
    generateBracket20230823(result, 0, 0, "", 3);
    System.out.println(result);

    result = new ArrayList<>();
    generateBracket20231206(result, 0, 0, "", 3);
    System.out.println(result);

  }

  private static void generateBracket20231206(ArrayList<String> result, int l, int r, String s, int k) {
  }

  private static void generateBracket20230823(ArrayList<String> result, int l, int r, String s, int k) {

    if (l == k && r == k) {
      result.add(s);
      return;
    }

    if (l < k) {
      generateBracket20230823(result, l+1, r, s+"(", k);
    }
    if (l>r && r < k) {
      generateBracket20230823(result, l, r +1, s + ")", k);
    }

  }

  private static void generateBracket20230811(ArrayList<String> result, int l, int r, String s, int n) {

    if (l==n && r ==n) {
      result.add(s);
      return;
    }

    if (l < n ) {
      generateBracket20230811(result, l+1, r, s+"(", n);
    }

    if (r<n&&l>r) {
      generateBracket20230808(result, l, r+1, s+")",n);
    }

  }

  private static void generateBracket20230808(ArrayList<String> result, int l, int r, String s, int n) {

    if (l == n && r == n) {
      result.add(s);
    }

    if (l < n) {
      generateBracket20230808(result, l+1, r, s+"(", n);
    }

    if (l>r &&r<n) {
      generateBracket20230808(result, l, r+1, s+")", n);
    }
  }

  private static void generateBracket(ArrayList<String> result, int left, int right, String s, int n) {
    if (left == n && right == n) {
       result.add(s);
       return;
    }
    if (left < n) {
      generateBracket(result, left+1, right, s+"(", n);
    }

    if (left>right && right < n) {
      generateBracket(result, left, right+1, s+")", n);
    }
  }


}
