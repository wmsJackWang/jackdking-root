package org.jackdking.common.utils;

import java.util.function.Consumer;

@FunctionalInterface
public interface PresentOrElseHandlerWithParam<T extends Object> {

    /**
     * 值不为空时执行消费操作
     * 值为空时执行其他的操作
     *
     * @param action 值不为空时，执行的消费操作
     * @param emptyAction 值为空时，执行的操作,可传递非final参数
     * @return void
     **/
    void presentOrElseHandle(RunnableWitchParam<? super T> action, RunnableWitchParam emptyAction);
}