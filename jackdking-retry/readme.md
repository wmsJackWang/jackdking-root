Java重试机制
重试作用：
对于重试是有场景限制的，不是什么场景都适合重试，比如参数校验不合法、写操作等（要考虑写是否幂等）都不适合重试。

远程调用超时、网络突然中断可以重试。在微服务治理框架中，通常都有自己的重试与超时配置，比如dubbo可以设置retries=1，timeout=500调用失败只重试1次，超过500ms调用仍未返回则调用失败。

比如外部 RPC 调用，或者数据入库等操作，如果一次操作失败，可以进行多次重试，提高调用成功的可能性。

优雅的重试机制要具备几点：
无侵入：这个好理解，不改动当前的业务逻辑，对于需要重试的地方，可以很简单的实现
可配置：包括重试次数，重试的间隔时间，是否使用异步方式等
通用性：最好是无改动（或者很小改动）的支持绝大部分的场景，拿过来直接可用
优雅重试共性和原理：
正常和重试优雅解耦，重试断言条件实例或逻辑异常实例是两者沟通的媒介。
约定重试间隔，差异性重试策略，设置重试超时时间，进一步保证重试有效性以及重试流程稳定性。
都使用了命令设计模式，通过委托重试对象完成相应的逻辑操作，同时内部封装实现重试逻辑。
Spring-tryer和guava-tryer工具都是线程安全的重试，能够支持并发业务场景的重试逻辑正确性。
优雅重试适用场景：
功能逻辑中存在不稳定依赖场景，需要使用重试获取预期结果或者尝试重新执行逻辑不立即结束。比如远程接口访问，数据加载访问，数据上传校验等等。
对于异常场景存在需要重试场景，同时希望把正常逻辑和重试逻辑解耦。
对于需要基于数据媒介交互，希望通过重试轮询检测执行逻辑场景也可以考虑重试方案。 
优雅重试解决思路：
切面方式
这个思路比较清晰，在需要添加重试的方法上添加一个用于重试的自定义注解，然后在切面中实现重试的逻辑，主要的配置参数则根据注解中的选项来初始化

优点：

真正的无侵入
缺点：

某些方法无法被切面拦截的场景无法覆盖（如spring-aop无法切私有方法，final方法）
直接使用aspecj则有些小复杂；如果用spring-aop，则只能切被spring容器管理的bean
消息总线方式
这个也比较容易理解，在需要重试的方法中，发送一个消息，并将业务逻辑作为回调方法传入；由一个订阅了重试消息的consumer来执行重试的业务逻辑

优点：

重试机制不受任何限制，即在任何地方你都可以使用
利用EventBus框架，可以非常容易把框架搭起来
缺点：

业务侵入，需要在重试的业务处，主动发起一条重试消息
调试理解复杂（消息总线方式的最大优点和缺点，就是过于灵活了，你可能都不知道什么地方处理这个消息，特别是新的童鞋来维护这段代码时）
如果要获取返回结果，不太好处理, 上下文参数不好处理
模板方式
优点：

简单（依赖简单：引入一个类就可以了； 使用简单：实现抽象类，讲业务逻辑填充即可；）
灵活（这个是真正的灵活了，你想怎么干都可以，完全由你控制）
缺点：

强侵入
代码臃肿
把这个单独捞出来，主要是某些时候我就一两个地方要用到重试，简单的实现下就好了，也没有必用用到上面这么重的方式；而且我希望可以针对代码快进行重试

这个的设计还是非常简单的，基本上代码都可以直接贴出来，一目了然：

复制代码
public abstract class RetryTemplate {

    private static final int DEFAULT_RETRY_TIME = 1;
    private int retryTime = DEFAULT_RETRY_TIME; 
    private int sleepTime = 0;// 重试的睡眠时间

    public int getSleepTime() {
        return sleepTime;
    }

    public RetryTemplate setSleepTime(int sleepTime) {
        if(sleepTime < 0) {
            throw new IllegalArgumentException("sleepTime should equal or bigger than 0");
        }
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public RetryTemplate setRetryTime(int retryTime) {
        if (retryTime <= 0) {
            throw new IllegalArgumentException("retryTime should bigger than 0");
        }
        this.retryTime = retryTime;
        return this;
    }

    /**
     * 重试的业务执行代码
     * 失败时请抛出一个异常
     *
     * todo 确定返回的封装类，根据返回结果的状态来判定是否需要重试
     *
     * @return
     */
    protected abstract Object doBiz() throws Exception; //预留一个doBiz方法由业务方来实现，在其中书写需要重试的业务代码，然后执行即可

    public Object execute() throws InterruptedException {
        for (int i = 0; i < retryTime; i++) {
            try {
                return doBiz();
            } catch (Exception e) {
                log.error("业务执行出现异常，e: {}", e);
                Thread.sleep(sleepTime);
            }
        }
        return null;
    }

    public Object submit(ExecutorService executorService) {
        if (executorService == null) {
            throw new IllegalArgumentException("please choose executorService!");
        }
        return executorService.submit((Callable) () -> execute());
    }
}
复制代码
使用示例：

复制代码
public void retryDemo() throws InterruptedException {
    Object ans = new RetryTemplate() {
        @Override
        protected Object doBiz() throws Exception {
            int temp = (int) (Math.random() * 10);
            System.out.println(temp);
            if (temp > 3) {
                throw new Exception("generate value bigger then 3! need retry");
            }
            return temp;
        }
    }.setRetryTime(10).setSleepTime(10).execute();
    System.out.println(ans);
}
复制代码
spring-retry
Spring Retry 为 Spring 应用程序提供了声明性重试支持。 它用于Spring批处理、Spring集成、Apache Hadoop(等等)的Spring。

在分布式系统中，为了保证数据分布式事务的强一致性，在调用RPC接口或者发送MQ时，针对可能会出现网络抖动请求超时情况采取一下重试操作。 用的最多的重试方式就是MQ了，但是如果你的项目中没有引入MQ，就不方便了。

还有一种方式，是开发者自己编写重试机制，但是大多不够优雅。

缺陷

spring-retry 工具虽能优雅实现重试，但是存在两个不友好设计：

一个是重试实体限定为 Throwable 子类，说明重试针对的是可捕捉的功能异常为设计前提的，但是我们希望依赖某个数据对象实体作为重试实体， 但 sping-retry框架必须强制转换为Throwable子类。
另一个是重试根源的断言对象使用的是 doWithRetry 的 Exception 异常实例，不符合正常内部断言的返回设计。
Spring Retry 提倡以注解的方式对方法进行重试，重试逻辑是同步执行的，当抛出相关异常后执行重试， 如果你要以返回值的某个状态来判定是否需要重试，可能只能通过自己判断返回值然后显式抛出异常了。只读操作可以重试，幂等写操作可以重试，但是非幂等写操作不能重试，重试可能导致脏写，或产生重复数据。

@Recover 注解在使用时无法指定方法，如果一个类中多个重试方法，就会很麻烦。

spring-retry 结构



BackOff：补偿值，一般指失败后多久进行重试的延迟值。
Sleeper：暂停应用的工具，通常用来应用补偿值。
RetryState：重试状态，通常包含一个重试的键值。
RetryCallback：封装你需要重试的业务逻辑（上文中的doSth）

RecoverCallback：封装了多次重试都失败后你需要执行的业务逻辑（上文中的doSthWhenStillFail）

RetryContext：重试语境下的上下文，代表了能被重试动作使用的资源。可用于在多次Retry或者Retry 和Recover之间传递参数或状态（在多次doSth或者doSth与doSthWhenStillFail之间传递参数）

RetryOperations： 定义了“重试”的模板（重试的API），要求传入RetryCallback，可选传入RecoveryCallback；

RetryTemplate ：RetryOperations的具体实现，组合了RetryListener[]，BackOffPolicy，RetryPolicy。
RetryListener：用来监控Retry的执行情况，并生成统计信息。

RetryPolicy：重试的策略或条件，可以简单的进行多次重试，可以是指定超时时间进行重试（上文中的someCondition），决定失败能否重试。

BackOffPolicy： 重试的回退策略，在业务逻辑执行发生异常时。如果需要重试，我们可能需要等一段时间(可能服务器过于繁忙，如果一直不间隔重试可能拖垮服务器)，当然这段时间可以是0，也可以是固定的，可以是随机的（参见tcp的拥塞控制算法中的回退策略）。回退策略在上文中体现为wait()；

RetryPolicy提供了如下策略实现：

NeverRetryPolicy：只允许调用RetryCallback一次，不允许重试；

AlwaysRetryPolicy：允许无限重试，直到成功，此方式逻辑不当会导致死循环；

SimpleRetryPolicy：固定次数重试策略，默认重试最大次数为3次，RetryTemplate默认使用的策略；

TimeoutRetryPolicy：超时时间重试策略，默认超时时间为1秒，在指定的超时时间内允许重试；

CircuitBreakerRetryPolicy：有熔断功能的重试策略，需设置3个参数openTimeout、resetTimeout和delegate

delegate：是真正判断是否重试的策略，当重试失败时，则执行熔断策略；应该配置基于次数的SimpleRetryPolicy或者基于超时的TimeoutRetryPolicy策略，且策略都是全局模式，而非局部模式，所以要注意次数或超时的配置合理性。

openTimeout：openWindow，配置熔断器电路打开的超时时间，当超过openTimeout之后熔断器电路变成半打开状态（主要有一次重试成功，则闭合电路）；

resetTimeout：timeout，配置重置熔断器重新闭合的超时时间

CompositeRetryPolicy：组合重试策略，有两种组合方式，乐观组合重试策略是指只要有一个策略允许重试即可以，悲观组合重试策略是指只要有一个策略不允许重试即可以，但不管哪种组合方式，组合中的每一个策略都会执行。

BackOffPolicy 提供了如下策略实现：

NoBackOffPolicy：无退避算法策略，即当重试时是立即重试；

FixedBackOffPolicy：固定时间的退避策略，需设置参数sleeper（指定等待策略，默认是Thread.sleep，即线程休眠）、backOffPeriod（休眠时间，默认1秒）；

UniformRandomBackOffPolicy：随机时间退避策略，需设置sleeper、minBackOffPeriod、maxBackOffPeriod，该策略在[minBackOffPeriod，maxBackOffPeriod之间取一个随机休眠时间，minBackOffPeriod默认500毫秒，maxBackOffPeriod默认1500毫秒；

ExponentialBackOffPolicy：指数退避策略，需设置参数sleeper、initialInterval、maxInterval和multiplier。initialInterval指定初始休眠时间，默认100毫秒，maxInterval指定最大休眠时间，默认30秒，multiplier指定乘数，即下一次休眠时间为当前休眠时间*multiplier；

ExponentialRandomBackOffPolicy：随机指数退避策略，引入随机乘数，固定乘数可能会引起很多服务同时重试导致DDos，使用随机休眠时间来避免这种情况。

RetryTemplate主要流程实现：

复制代码
//示例一
public void upload(final Map<String, Object> map) throws Exception {
        // 构建重试模板实例
        RetryTemplate retryTemplate = new RetryTemplate();
        // 设置重试策略，主要设置重试次数
        SimpleRetryPolicy policy = 
　　　　　　　　new SimpleRetryPolicy(3, Collections.<Class<? extends Throwable>, Boolean> singletonMap(Exception.class, true));
        // 设置重试回退操作策略，主要设置重试间隔时间
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100);
        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        // 通过RetryCallback 重试回调实例包装正常逻辑逻辑，第一次执行和重试执行执行的都是这段逻辑
        final RetryCallback<Object, Exception> retryCallback = new RetryCallback<Object, Exception>() {
            //RetryContext 重试操作上下文约定，统一spring-try包装 
            public Object doWithRetry(RetryContext context) throws Exception {
                System.out.println("do some thing");
                Exception e = uploadToOdps(map);
                System.out.println(context.getRetryCount());
                throw e;//这个点特别注意，重试的根源通过Exception返回
            }
        };
        // 通过RecoveryCallback 重试流程正常结束或者达到重试上限后的退出恢复操作实例
        final RecoveryCallback<Object> recoveryCallback = new RecoveryCallback<Object>() {
            public Object recover(RetryContext context) throws Exception {
                System.out.println("do recory operation");
                return null;
            }
        };
        try {
            // 由retryTemplate 执行execute方法开始逻辑执行
            retryTemplate.execute(retryCallback, recoveryCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//示例二
protected <T, E extends Throwable> T doExecute(RetryCallback<T, E> retryCallback,RecoveryCallback<T> recoveryCallback, 
　　RetryState state)  throws E, ExhaustedRetryException {
   //重试策略
   RetryPolicy retryPolicy = this.retryPolicy;
   //退避策略
   BackOffPolicy backOffPolicy = this.backOffPolicy;
   //重试上下文，当前重试次数等都记录在上下文中
   RetryContext context = open(retryPolicy, state);
   try {
      //拦截器模式，执行RetryListener#open
      boolean running = doOpenInterceptors(retryCallback, context);
      //判断是否可以重试执行
      while (canRetry(retryPolicy, context) && !context.isExhaustedOnly()) {
         try {//执行RetryCallback回调
            return retryCallback.doWithRetry(context);
         } catch (Throwable e) {//异常时，要进行下一次重试准备
            //遇到异常后，注册该异常的失败次数
            registerThrowable(retryPolicy, state, context, e);
            //执行RetryListener#onError
            doOnErrorInterceptors(retryCallback, context, e);
            //如果可以重试，执行退避算法，比如休眠一小段时间后再重试
            if (canRetry(retryPolicy, context) && !context.isExhaustedOnly()) {
               backOffPolicy.backOff(backOffContext);
            }
            //state != null && state.rollbackFor(context.getLastThrowable())
            //在有状态重试时，如果是需要执行回滚操作的异常，则立即抛出异常
            if (shouldRethrow(retryPolicy, context, state)) {
               throw RetryTemplate.<E>wrapIfNecessary(e);
            }
         }
         //如果是有状态重试，且有GLOBAL_STATE属性，则立即跳出重试终止；
　　　　　 //当抛出的异常是非需要执行回滚操作的异常时，才会执行到此处，CircuitBreakerRetryPolicy会在此跳出循环；
         if (state != null && context.hasAttribute(GLOBAL_STATE)) {
            break;
         }
      }
      //重试失败后，如果有RecoveryCallback，则执行此回调，否则抛出异常
      return handleRetryExhausted(recoveryCallback, context, state);
   } catch (Throwable e) {
      throw RetryTemplate.<E>wrapIfNecessary(e);
   } finally {
      //清理环境
      close(retryPolicy, context, state, lastException == null || exhausted);
      //执行RetryListener#close，比如统计重试信息
      doCloseInterceptors(retryCallback, context, lastException);
   }
}
复制代码
有状态or无状态
无状态重试，是在一个循环中执行完重试策略，即重试上下文保持在一个线程上下文中，在一次调用中进行完整的重试策略判断。如远程调用某个查询方法时是最常见的无状态重试：
复制代码
RetryTemplate template = new RetryTemplate();
//重试策略：次数重试策略
RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
template.setRetryPolicy(retryPolicy);
//退避策略：指数退避策略
ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
backOffPolicy.setInitialInterval(100);
backOffPolicy.setMaxInterval(3000);
backOffPolicy.setMultiplier(2);
backOffPolicy.setSleeper(new ThreadWaitSleeper());
template.setBackOffPolicy(backOffPolicy);

//当重试失败后，抛出异常
String result = template.execute(new RetryCallback<String, RuntimeException>() {
    @Override
    public String doWithRetry(RetryContext context) throws RuntimeException {
        throw new RuntimeException("timeout");
    }
});
//当重试失败后，执行RecoveryCallback
String result = template.execute(new RetryCallback<String, RuntimeException>() {
    @Override
    public String doWithRetry(RetryContext context) throws RuntimeException {
        System.out.println("retry count:" + context.getRetryCount());
        throw new RuntimeException("timeout");
    }
}, new RecoveryCallback<String>() {
    @Override
    public String recover(RetryContext context) throws Exception {
        return "default";
    }
});
复制代码
有状态重试，有两种情况需要使用有状态重试，事务操作需要回滚、熔断器模式。
事务操作需要回滚场景时，当整个操作中抛出的是数据库异常DataAccessException，则不能进行重试需要回滚，而抛出其他异常则可以进行重试，可以通过RetryState实现：

复制代码
//当前状态的名称，当把状态放入缓存时，通过该key查询获取
Object key = "mykey";
//是否每次都重新生成上下文还是从缓存中查询，即全局模式（如熔断器策略时从缓存中查询）
boolean isForceRefresh = true;
//对DataAccessException进行回滚
BinaryExceptionClassifier rollbackClassifier =
        new BinaryExceptionClassifier(Collections.<Class<? extends Throwable>>singleton(DataAccessException.class));
RetryState state = new DefaultRetryState(key, isForceRefresh, rollbackClassifier);

String result = template.execute(new RetryCallback<String, RuntimeException>() {
    @Override
    public String doWithRetry(RetryContext context) throws RuntimeException {
        System.out.println("retry count:" + context.getRetryCount());
        throw new TypeMismatchDataAccessException("");
    }
}, new RecoveryCallback<String>() {
    @Override
    public String recover(RetryContext context) throws Exception {
        return "default";
    }
}, state);
复制代码
RetryTemplate中在有状态重试时，回滚场景时直接抛出异常处理代码：

//state != null && state.rollbackFor(context.getLastThrowable())
//在有状态重试时，如果是需要执行回滚操作的异常，则立即抛出异常
if (shouldRethrow(retryPolicy,context, state)) {
    throw RetryTemplate.<E>wrapIfNecessary(e);
}
熔断器场景。在有状态重试时，且是全局模式，不在当前循环中处理重试，而是全局重试模式（不是线程上下文），如熔断器策略时测试代码如下所示。

复制代码
RetryTemplate template = new RetryTemplate();
CircuitBreakerRetryPolicy retryPolicy =
        new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(3));
retryPolicy.setOpenTimeout(5000);
retryPolicy.setResetTimeout(20000);
template.setRetryPolicy(retryPolicy);

for (int i = 0; i < 10; i++) {
    try {
        Object key = "circuit";
        boolean isForceRefresh = false;
        RetryState state = new DefaultRetryState(key, isForceRefresh);
        String result = template.execute(new RetryCallback<String, RuntimeException>() {
            @Override
            public String doWithRetry(RetryContext context) throws RuntimeException {
                System.out.println("retry count:" + context.getRetryCount());
                throw new RuntimeException("timeout");
            }
        }, new RecoveryCallback<String>() {
            @Override
            public String recover(RetryContext context) throws Exception {
                return "default";
            }
        }, state);
        System.out.println(result);
    } catch (Exception e) {
        System.out.println(e);
    }
}
复制代码
为什么说是全局模式呢？我们配置了isForceRefresh为false，则在获取上下文时是根据key “circuit”从缓存中获取，从而拿到同一个上下文。

Object key = "circuit";
boolean isForceRefresh = false;
RetryState state = new DefaultRetryState(key,isForceRefresh); 
如下RetryTemplate代码说明在有状态模式下，不会在循环中进行重试。

if (state != null && context.hasAttribute(GLOBAL_STATE)) {
   break;
}
判断熔断器电路是否打开的代码：

复制代码
public boolean isOpen() {
   long time = System.currentTimeMillis() - this.start;
   boolean retryable = this.policy.canRetry(this.context);
   if (!retryable) {//重试失败
      //在重置熔断器超时后，熔断器器电路闭合，重置上下文
      if (time > this.timeout) {
         this.context = createDelegateContext(policy, getParent());
         this.start = System.currentTimeMillis();
         retryable = this.policy.canRetry(this.context);
      } else if (time < this.openWindow) {
         //当在熔断器打开状态时，熔断器电路打开，立即熔断
         if ((Boolean) getAttribute(CIRCUIT_OPEN) == false) {
            setAttribute(CIRCUIT_OPEN, true);
         }
         this.start = System.currentTimeMillis();
         return true;
      }
   } else {//重试成功
      //在熔断器电路半打开状态时，断路器电路闭合，重置上下文
      if (time > this.openWindow) {
         this.start = System.currentTimeMillis();
         this.context = createDelegateContext(policy, getParent());
      }
   }
   setAttribute(CIRCUIT_OPEN, !retryable);
   return !retryable;
}
复制代码
从如上代码可看出spring-retry的熔断策略相对简单：

当重试失败，且在熔断器打开时间窗口[0,openWindow) 内，立即熔断；

当重试失败，且在指定超时时间后(>timeout)，熔断器电路重新闭合；

在熔断器半打开状态[openWindow, timeout] 时，只要重试成功则重置上下文，断路器闭合。

注解介绍
@EnableRetry
表示是否开始重试。

序号	属性	类型	默认值	说明
1	proxyTargetClass	boolean	false	指示是否要创建基于子类的(CGLIB)代理，而不是创建标准的基于Java接口的代理。当proxyTargetClass属性为true时，使用CGLIB代理。默认使用标准JAVA注解
@Retryable
标注此注解的方法在发生异常时会进行重试

序号	属性	类型	默认值	说明
1	interceptor	String	””	将 interceptor 的 bean 名称应用到 retryable()
2	value	class[]	{}	可重试的异常类型
3	include	class[]	{}	和value一样，默认空，当exclude也为空时，所有异常都重试
4	exclude	class[]	{}	指定异常不重试，默认空，当include也为空时，所有异常都重试 
5	label	String	””	统计报告的唯一标签。如果没有提供，调用者可以选择忽略它，或者提供默认值。
6	maxAttempts	int	3	尝试的最大次数(包括第一次失败)，默认为3次。
7	backoff	@Backoff	@Backoff()	重试补偿机制，指定用于重试此操作的backoff属性。默认为空
@Backoff
不设置参数时，默认使用FixedBackOffPolicy（指定等待时间），重试等待1000ms

序号	属性	类型	默认值	说明
1	delay	long	0	指定延迟后重试 ，如果不设置则默认使用 1000 milliseconds
2	maxDelay	long	0	最大重试等待时间
3	multiplier	long	0	指定延迟的倍数，比如delay=5000l,multiplier=2时，第一次重试为5秒后，第二次为10秒，第三次为20秒(大于0生效)
4	random	boolean	false	随机重试等待时间
@Recover
用于恢复处理程序的方法调用的注释。返回类型必须与@retryable方法匹配。 可抛出的第一个参数是可选的(但是没有它的方法只会被调用)。 从失败方法的参数列表按顺序填充后续的参数。

用于@Retryable重试失败后处理方法，此注解注释的方法参数一定要是@Retryable抛出的异常，否则无法识别，可以在该方法中进行日志处理。

说明：

使用了@Retryable的方法不能在本类被调用，不然重试机制不会生效。也就是要标记为@Service，然后在其它类使用@Autowired注入或者@Bean去实例才能生效。
要触发@Recover方法，那么在@Retryable方法上不能有返回值，只能是void才能生效。
使用了@Retryable的方法里面不能使用try...catch包裹，要在发放上抛出异常，不然不会触发。
在重试期间这个方法是同步的，如果使用类似Spring Cloud这种框架的熔断机制时，可以结合重试机制来重试后返回结果。
Spring Retry不只能注入方式去实现，还可以通过API的方式实现，类似熔断处理的机制就基于API方式实现会比较宽松。