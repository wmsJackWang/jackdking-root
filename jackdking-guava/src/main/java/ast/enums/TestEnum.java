package ast.enums;

import org.jackdking.common.ast.annotation.EnumInnerConstant;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TestEnum
 * @Description TODO
 * @Author jackdking
 * @Date 30/03/2022 11:20 上午
 * @Version 2.0
 **/
@EnumInnerConstant(innerClassName = "Great")
public enum TestEnum {
    Hello(1, "java");
    private int num;
    private String message;
    TestEnum (int num, String message){
        this.num = num;
        this.message = message;

    }
}
