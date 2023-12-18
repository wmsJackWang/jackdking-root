package decision.bo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import decision.constant.DSLConstant;
import decision.service.DecisionTreeContext;
import decision.service.GroovyScriptRunner;
import groovy.lang.Closure;
import groovy.lang.Tuple2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DecisionTreeAgentTest
 * @Description TODO
 * @Author jackdking
 * @Date 24/11/2023 4:07 下午
 * @Version 2.0
 **/
public class DecisionTreeAgentTest {


  private static final Map<String, Object> params = Maps.newHashMap();

  @Test
  public void testDecisionTreeAgentInit(){

    params.put("事件状态", "waiting_deal");
//        params.put("days", 8);
    params.put("days", 16);
//        params.put("days", 33);
    String content = GroovyScriptRunner.getScriptContent("params", "decisionTreeInt1", params);
    DecisionTreeAgent strategyDelegate = DecisionTreeAgent.of(content);
    System.out.println(JSON.toJSONString(strategyDelegate.getReturnAttrs()));
    System.out.println(JSON.toJSONString(strategyDelegate.getAttrs()));
    System.out.println(JSON.toJSONString(strategyDelegate.getGlobalFunctions()));

    DecisionTreeContext context = new DecisionTreeContext();
    HashMap<String, Object> data = new HashMap<>();
    data.put("age", 30);
    context.setData(data);
    for (Tuple2<String, Closure> node : strategyDelegate.getNodes()) {
      if (node.getV1() == DSLConstant.Operate) {
        Object result = node.getV2().call(context);
        System.out.println("node result:" + result);
      }
    }
  }
}
