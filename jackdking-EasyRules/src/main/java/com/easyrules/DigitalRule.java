package com.easyrules;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DigitalRule
 * @Description TODO
 * @Author jackdking
 * @Date 06/04/2022 7:39 下午
 * @Version 2.0
 **/

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

public class DigitalRule {
    @Rule(name = "fizzRule", description = "能否被5整除", priority = 1)
    public static class FizzRule {
        @Condition
        public boolean isFizz(@Fact("input") int input) {
            return input % 5 == 0;
        }

        @Action
        public void printFizz(@Fact("input") int input) {
            System.out.println(input + "：能被5整除（fizz）");
        }
    }

    @Rule(name = "buzzRule", description = "能否被7整除", priority = 2)
    public static class BuzzRule {
        @Condition
        public boolean isBuzz(@Fact("input") int input) {
            return input % 7 == 0;
        }

        @Action
        public void printBuzz(@Fact("input") int input) {
            System.out.println(input + "：能被7整除（buzz）");
        }

    }

    @Rule(name = "nonFizzBuzzRule", description = "不能被5整除 或 不能被7整除", priority = 3)
    public static class NonFizzOrBuzzRule {
        @Condition
        public boolean isNotFizzOrBuzz(@Fact("input") int input) {
            return input % 5 != 0 || input % 7 != 0;
        }

        @Action
        public void printInput(@Fact("input") int input) {
            System.out.println(input + "：不能被5整除 或 不能被7整除");
        }
    }

    @Rule(name = "nonFizzBuzzRule", description = "不能被5整除 且 不能被7整除", priority = 4)
    public static class NonFizzAndBuzzRule {
        @Condition
        public boolean isNotFizzAndBuzz(@Fact("input") int input) {
            return input % 5 != 0 && input % 7 != 0;
        }

        @Action
        public void printInput(@Fact("input") int input) {
            System.out.println(input + "：不能被5整除 且 不能被7整除");
        }
    }

}

