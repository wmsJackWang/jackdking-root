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
