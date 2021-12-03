package org.jackdking.limiter.aspect;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jackdking.limiter.anoation.AccessLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Boolean> limitUserAccessLua;

    // 切入点
    @Pointcut("@annotation(org.jackdking.limiter.anoation.AccessLimiter)")
    public void cut() {
        System.out.println("cut");
    }

    // 通知和连接点
    @Before("cut()")
    public void before(JoinPoint joinPoint) throws Exception {

        // 获取到执行的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 通过方法获取到注解
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        // 如果 annotation==null，说明方法上没加限流AccessLimiter,说明不需要限流操作
        if (annotation == null) {
            return;
        }
        // 获取到对应的注解参数
        String key = annotation.key();
        Integer limit = annotation.limit();
        Integer timeout = annotation.timeout();

        // 如果key为空
        if (StringUtils.isEmpty(key)) {
            String name = method.getDeclaringClass().getName();
            // 直接把当前的方法名给与key
            key = name+"#"+method.getName();
            // 获取方法中的参数列表

            //ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
            //String[] parameterNames = pnd.getParameterNames(method);

            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.println(parameterType);
            }

            // 如果方法有参数，那么就把key规则 = 方法名“#”参数类型
            if (parameterTypes != null) {
                String paramTypes = Arrays.stream(parameterTypes)
                        .map(Class::getName)
                        .collect(Collectors.joining(","));
                key = key +"#" + paramTypes;
            }
        }

        // 定义key是的列表
        List<String> keysList = new ArrayList<>();
        keysList.add(key);
        // 执行执行lua脚本限流
        Boolean accessFlag = stringRedisTemplate.execute(limitUserAccessLua, keysList, limit.toString(), timeout.toString());
        // 判断当前执行的结果，如果是0，被限制，1代表正常
        if (!accessFlag) {
            throw new Exception("server is busy!!!");
        }
    }
}
