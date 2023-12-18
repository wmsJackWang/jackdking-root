package org.jackdking.common;

import junit.framework.TestCase;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.jackdking.common.utils.CodeFlowUtils;

import java.util.Optional;

/**
 * Copyright (C) °¢Àï°Í°Í
 *
 * @ClassName CodeFlowUtilsTest
 * @Description TODO
 * @Author jackdking
 * @Date 10/04/2022 12:43 ÏÂÎç
 * @Version 2.0
 **/
public class CodeFlowUtilsTest extends TestCase {


    public void testCodeFlowUtilsAcceptIf() {
        CodeFlowUtils.acceptIf(true, o -> o==Boolean.TRUE, o -> System.out.println("right result"));

        Optional.ofNullable(true)
                .filter(o -> o==Boolean.TRUE)
                .ifPresent(o -> System.out.println("right result"));
    }

    public void testCodeFlowUtilsAcceptIfElse() {
        CodeFlowUtils.acceptIfElse(false, o -> o == Boolean.TRUE
                , o -> {System.out.println("right result");}
                , o -> System.out.println("wrong result"));
    }

    public void testCodeFlowAcceptIfElseWithReturn() {
        Boolean result = CodeFlowUtils.acceptIfElse(false, o -> o == Boolean.FALSE
                , o -> Boolean.TRUE
                , o -> Boolean.FALSE);

        System.out.println(result);
    }
}
