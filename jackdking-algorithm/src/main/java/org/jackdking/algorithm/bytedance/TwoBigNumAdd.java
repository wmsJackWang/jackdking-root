package org.jackdking.algorithm.bytedance;

import java.security.SecureRandom;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TwoBigNumAdd
 * @Description TODO
 * @Author jackdking
 * @Date 18/03/2023 10:19 下午
 * @Version 2.0
 **/
public class TwoBigNumAdd {

  public static void main(String[] args) {

    String input1 = "12", input2 = "100";
    //这个只能支持小数，超大的数是无法支持的。
    caculateBigNum(input1, input2);
    input1 = "733064366";
    input2 = "459309139";
    System.out.println(caculateBigNum20230318(input1, input2));


  }

  private static String  caculateBigNum20230318(String input1, String input2) {

    if (input1 == null || input1.equalsIgnoreCase("")) {
      return input2;
    }
    if (input2 == null || input2.equalsIgnoreCase("")) {
      return input1;
    }

    char[] input1Chars = input1.toCharArray();
    char[] input2Chars = input2.toCharArray();

    int maxLength = input1Chars.length > input2Chars.length ? input1Chars.length + 1:input2Chars.length + 1;
    char[] resultChars = new char[maxLength];
    int k1, k2, v = 0, val;
    for (int i = maxLength - 1 ; i >= 0 ; i--) {
      int index1 = input1Chars.length - (maxLength - 1 - i) - 1;
      if (index1 >= 0) {
        k1 = input1Chars[index1] - '0';
      }else {
        k1 = 0;
      }

      int index2 = input2Chars.length - (maxLength - 1 - i) - 1;
      if (index2 >= 0) {
        k2 = input2Chars[index2] - '0';
      }else {
        k2 = 0;
      }
      val = k1 + k2 + v;
      resultChars[i] = (char) (val%10 + '0');
      if (val/10 == 1) {
        v = 1;
      }else {
        v = 0;
      }
    }
    if (resultChars[0] == '0') {
      return new String(resultChars, 1, resultChars.length - 1);
    }
    return new String(resultChars);
  }

  private static String caculateBigNum(String input1, String input2) {
    int result = parseIntFromString(input1) + parseIntFromString(input2);
    System.out.println("result:"+ result);
    return result + "";
  }

  public static int parseIntFromString(String input) {
    if (input == null || input.equalsIgnoreCase("")) {
      return 0;
    }
    char[] chars = input.toCharArray();
    int num = 0, k = 1;
    for (int i = chars.length - 1 ; i >= 0 ; i --, k *= 10) {
      num += (chars[i] - '0') * k;
    }
    return num;
  }
}
