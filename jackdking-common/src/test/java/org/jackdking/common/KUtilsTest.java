package org.jackdking.common;

import junit.framework.TestCase;
import org.jackdking.common.utils.StreamCodeUtils;

public class KUtilsTest extends TestCase {

    public void testThrowError (){

        StreamCodeUtils.requireTure(true).throwMessage("参数错误，请求被打回");

    }


    /*
     * isTureOrFalse方法体只能使用final修饰的对象，非final的变量无法使用，因此解决方案请看下面一个test方法。
     */
    public void testIfElse (){
        String successMessage = "hello world";
        successMessage = "good";
        String successMsg = successMessage;

        String falseMessage = "hello god";

        StreamCodeUtils.isTureOrFalse(true).trueOrFalseHandle(() -> {
            System.out.println(successMsg);
        }, () ->{
            System.out.println(falseMessage);
        });
    }



    /*
     * 这是一个可以传递参数的if...else分支代码
     */
    public void testIfElseWithParameter () {

        String successMessage = "hello world";
        successMessage = "success";
        String falseMessage = "hello god";
        falseMessage = "false";

        StreamCodeUtils.isTureOrFalseWithParameter(false, successMessage, falseMessage).handle((parameters) -> {
            System.out.println(parameters[0]);
        }, (parameters) -> {
            System.out.println(parameters[1]);
        });
    }

    public void testPresentOrElseHandler (){

        String successMessage = "hello world";
        successMessage = "";

        StreamCodeUtils.isBlankOrNoBlank(successMessage).presentOrElseHandle(System.out::println, () -> {
            System.out.println("empty string");
        });

    }

    public void testPresentOrElseHandlerWithParam (){


        String successMessage = "hello world";
        successMessage = "1";
        String remindMsg = "hello god";
        remindMsg = "empty string";

        StreamCodeUtils.isBlankOrNoBlankWithParam(successMessage, successMessage, remindMsg).presentOrElseHandle((parameters) -> {
            System.out.println(parameters[0]);
        }, (parameters) -> {
            System.out.println(parameters[1]);
        });

    }

    public void testIsTrueOrElseHandler (){

    }

}
