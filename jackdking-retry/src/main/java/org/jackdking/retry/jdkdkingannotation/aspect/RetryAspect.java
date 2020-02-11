package org.jackdking.retry.jdkdkingannotation.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jackdking.core.common.JdkApplicationContext;
import org.jackdking.retry.jdkdkingannotation.annotation.JdkRetry;
import org.jackdking.retry.jdkdkingannotation.recover.Recover;
import org.jackdking.retry.jdkdkingannotation.retryException.UpdateRetryException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class RetryAspect {
	/**
	 * 默认重试几次
	 */
	private static final int    DEFAULT_MAX_RETRIES = 3;

	private int                 maxRetries          = DEFAULT_MAX_RETRIES;

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}
	

	@Transactional(rollbackFor = Exception.class)
	@Around("@annotation(jdkRetry)")
	//开发自定义注解的时候，定要注意  @annotation(jdkRetry)和下面方法的参数，按规定是固定的形式的，否则报错
	public Object doConcurrentOperation(ProceedingJoinPoint pjp , JdkRetry jdkRetry) throws Throwable {
		//获取注解的属性
//		pjp.getClass().getMethod(, parameterTypes)
		System.out.println("切面作用："+jdkRetry.maxAttempts()+ "  恢复策略类："+ jdkRetry.recoverServiceName());
		
		Object service = JdkApplicationContext.jdkApplicationContext.getBean(jdkRetry.recoverServiceName());
		Recover recover = null;
		if(service == null)
			return new Exception("recover处理服务实例不存在");
		recover = (Recover)service;
		
		long waitTime =  jdkRetry.waitTime();
		maxRetries = jdkRetry.maxAttempts();
		Class<?> exceptionClass = jdkRetry.exception();
		
		
		int numAttempts = 0;
		do {
			numAttempts++;
			try {
				//再次执行业务代码
				return pjp.proceed();
			} catch (Exception ex) {
				//必须只是乐观锁更新才能进行重试逻辑
				System.out.println(ex.getClass().getName());
				if(!ex.getClass().getName().equals(exceptionClass.getName()))
					throw ex;
				if (numAttempts > maxRetries) {
					
					recover.recover(null);
					//log failure information, and throw exception
//					如果大于 默认的重试机制 次数，我们这回就真正的抛出去了
//					throw new Exception("重试逻辑执行完成，业务还是失败!");
				}else{
					//如果 没达到最大的重试次数，将再次执行
					System.out.println("=====正在重试====="+numAttempts+"次");
					TimeUnit.MILLISECONDS.sleep(waitTime);
				}
			}
		} while (numAttempts <= this.maxRetries);

		return 500;
	}
}