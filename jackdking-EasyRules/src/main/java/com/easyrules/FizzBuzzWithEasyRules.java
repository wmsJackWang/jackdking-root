package com.easyrules;

import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;


/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FizzBuzzWithEasyRules
 * @Description TODO
 * @Author jackdking
 * @Date 06/04/2022 8:24 下午
 * @Version 2.0
 **/
public class FizzBuzzWithEasyRules {
    public static void main(String[] args) {
        RulesEngineParameters parameters = new RulesEngineParameters()
                .skipOnFirstAppliedRule(false)//告诉引擎规则被触发时跳过后面的规则。
                .skipOnFirstFailedRule(false)//告诉引擎在规则失败时跳过后面的规则。
                .skipOnFirstNonTriggeredRule(false);//告诉引擎一个规则不被触发，就跳过后面的规则。

        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // register rules
        Rules rules = new Rules();
        rules.register(new DigitalRule.FizzRule());
        rules.register(new DigitalRule.BuzzRule());
        rules.register(new DigitalRule.NonFizzAndBuzzRule());
        rules.register(new DigitalRule.NonFizzOrBuzzRule());
        // fire rules
        Facts facts = new Facts();
        for (int i = 0; i <= 100; i++) {
            facts.put("input", i);
            fizzBuzzEngine.fire(rules, facts);
        }
    }
}
