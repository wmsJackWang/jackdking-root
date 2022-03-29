package org.jackdking.common.utils;

import java.util.function.Predicate;


public class StreamCodeUtils {


    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param condition
     * @return org.jackdking.common.utils.BranchHandle
     **/
    public static BranchHandle isTureOrFalse (boolean condition){

        return (trueHandle, falseHandle) -> {
            if (condition){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /*
     * 参数条件执行 为true或false时，分别进行不同的操作
     *
     * @param value
     * @param predicate
     * @return org.jackdking.common.utils.BranchHandle
     */
    public static <T> BranchHandle isTureOrFalse (T value, Predicate predicate){

        return (trueHandle, falseHandle) -> {
            if (predicate.test(value)){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    public static BranchHandleWithParam isTureOrFalseWithParameter (boolean condition, Object... parameters){

        return (trueHandle, falseHandle) -> {
            if (condition){
                trueHandle.run(parameters);
            } else {
                falseHandle.run(parameters);
            }
        };
    }


    public static <T> BranchHandleWithParam isTureOrFalseWithParameter (T value, Predicate predicate, Object... parameters){

        return (trueHandle, falseHandle) -> {
            if (predicate.test(value)){
                trueHandle.run(parameters);
            } else {
                falseHandle.run(parameters);
            }
        };
    }

    /**
     *  如果参数为true抛出异常
     *
     * @param condition
     * @return org.jackdking.common.utils.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction requireTure(boolean condition){

        return (errorMessage) -> {
            if (!condition){
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     *  如果参数为true抛出异常
     *
     * @param predicate
     * @return org.jackdking.common.utils.ThrowExceptionFunction
     **/
    public static <T> ThrowExceptionFunction requireTure(T value, Predicate predicate){

        return (errorMessage) -> {
            if (!predicate.test(value)){
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param str
     * @return org.jackdking.common.utils.BranchHandle
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }

    public static PresentOrElseHandlerWithParam<?> isBlankOrNoBlankWithParam(String str, Object... params){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run(params);
            } else {
                consumer.run(params);
            }
        };
    }

}
