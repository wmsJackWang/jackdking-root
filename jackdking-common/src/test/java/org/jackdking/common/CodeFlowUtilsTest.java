package org.jackdking.common;

import junit.framework.TestCase;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.jackdking.common.utils.CodeFlowUtils;

public class CodeFlowUtilsTest extends TestCase {


    public void testCodeFlowUtilsAcceptIf() {
        CodeFlowUtils.acceptIf(false, o -> o==Boolean.TRUE, o -> System.out.println("right result"));
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
