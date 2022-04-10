package ast.enums;

import org.jackdking.common.ast.annotation.EnumInnerConstant;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Person
 * @Description TODO
 * @Author jackdking
 * @Date 30/03/2022 4:43 下午
 * @Version 2.0
 **/
@EnumInnerConstant(innerClassName = "Test")
public class Person {
    private int age = 12;

    public static void main(String[] args) {
        System.out.println(columnTest.toString());
    }
}
