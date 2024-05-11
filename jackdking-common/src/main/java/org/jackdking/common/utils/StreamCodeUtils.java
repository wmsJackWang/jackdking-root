package org.jackdking.common.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.function.Predicate;


public class StreamCodeUtils<T> {

    private StreamCodeBuilder streamCodeBuilder;

    static class StreamCodeBuilder<T>{
        T value;
        Runnable positiveHandler;
        Runnable negativeHandler;

//        Runnable defaultHandler = o -> o;

        public StreamCodeBuilder(T value) {
            this.value = value;
        }

        public Boolean result() {return Boolean.valueOf(String.valueOf(value));};

        public void handle() {

            if (result()){
                positiveHandler.run();
            } else {
                negativeHandler.run();
            }
        }
    }

    public StreamCodeUtils (T vlaue){
        streamCodeBuilder = new StreamCodeBuilder(vlaue);
    }

    public static StreamCodeUtils of(Boolean value) {
        return new StreamCodeUtils(value);
}

    public StreamCodeUtils positiveHandler(Runnable positiveHandler) {
        this.streamCodeBuilder.positiveHandler = positiveHandler;
        return this;
    }

    public StreamCodeUtils negativeHandler(Runnable negativeHandler) {
        this.streamCodeBuilder.negativeHandler = negativeHandler;
        return this;
    }

    public void handle() {
        this.streamCodeBuilder.handle();
    }


    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param condition
     * @return org.jackdking.common.utils.BranchHandle
     **/
    public static BranchHandler isTureOrFalse (boolean condition){

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
    public static <T> BranchHandler isTureOrFalse (T value, Predicate predicate){

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


    public static <T> BranchHandleWithParam isTureOrFalseWithParameter (T value, Predicate predicate, T... parameters){

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
    public static PresentOrElseHandler isBlankOrNoBlank(String str){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }

    public static <T> PresentOrElseHandlerWithParam isBlankOrNoBlankWithParam(String str, T... params){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run(params);
            } else {
                consumer.run(params);
            }
        };
    }

}
