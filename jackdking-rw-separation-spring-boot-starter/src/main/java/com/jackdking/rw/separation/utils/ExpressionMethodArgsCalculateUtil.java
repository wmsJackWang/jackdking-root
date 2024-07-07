package com.jackdking.rw.separation.utils;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
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
public class ExpressionMethodArgsCalculateUtil {

  /**
   * 用于获取方法参数定义名字.
   */
  private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
  /**
   * 用于SpEL表达式解析.
   */
  private static final SpelExpressionParser parser = new SpelExpressionParser();

  /**
   * @param context：el表达式
   * @param expr：el表达式动态参数
   * @return
   */
  private static String getExpValue(MethodBasedEvaluationContext context, String expr) {
    Expression expression = parser.parseExpression(expr);
    return (String) expression.getValue(context);
  }

  public static String methodArgsExpressionCalculate(String expression, Method targetMethod, Object[] args) {

    MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(TypedValue.NULL, targetMethod, args, parameterNameDiscoverer);
    return getExpValue(context, expression);
  }
}
