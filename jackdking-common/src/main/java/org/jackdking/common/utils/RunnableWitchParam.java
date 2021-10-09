package org.jackdking.common.utils;

@FunctionalInterface
public interface RunnableWitchParam<T extends Object> {

    void run(T... parameters);

}
