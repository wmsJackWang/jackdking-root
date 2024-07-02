package org.jackdking.aop.log.aop;

import java.util.Objects;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jackdking.aop.log.anoation.OperationLog;

@Aspect
public class OperationLogAspect {

	  //匹配OperationLog注解类
    @Around("@annotation(org.jackdking.aop.log.anoation.OperationLog)")
    public Object addLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        //如果返回值为null,则返回null
        if (Objects.isNull(result)) {
            return null;
        }
        OperationLog operationLog = (OperationLog) point.getSignature().getDeclaringType().getAnnotation(OperationLog.class);

        System.out.println(operationLog.value());

        return result;
    }

}
