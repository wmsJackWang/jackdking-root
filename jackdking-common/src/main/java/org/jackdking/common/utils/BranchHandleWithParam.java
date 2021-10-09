package org.jackdking.common.utils;

@FunctionalInterface
public interface BranchHandleWithParam {

    public void handle(RunnableWitchParam trueHandle, RunnableWitchParam falseHandle);
}
