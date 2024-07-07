package com.jackdking.rw.separation.utils;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ExpressionLogicCalculateUtil
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/7 13:07
 * @Version 2.0
 **/
public class ExpressionLogicCalculateUtil {

  /**
   * @param express：el表达式
   * @param map：el表达式动态参数
   * @return
   */
  public static <T> String parse(String express, Map<String, T> map) {
    StringSubstitutor sub = new StringSubstitutor(map);
    return sub.replace(express);
  }

  public static boolean logicCalculate(String expression, Map<String, Object> valMap) {

    String finalExpress = parse(expression, valMap);
    ExpressionParser parser = new SpelExpressionParser();
    boolean logicResult = parser.parseExpression(finalExpress).getValue(Boolean.class);
    return logicResult;
  }


}
