package CompletableFutureTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ScopeMetadata;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureTest {

    public static void print(String msg) {
        log.info("{}", msg);
//        System.out.println("[threadId:"+Thread.currentThread().getId() + "]" + msg);
    }

    @Test
    public void testRunB_AfterA_Consumer() {
        //注意： 共四次打印操作，第二个跟第一个肯定不一样（异步）, 第三个可能跟第二个一样，也可能跟第一个一样。
        print("main thread start");
        CompletableFuture.runAsync(() -> print("task a")).thenAccept(o -> print("action b"));
        print("main thread end");
    }

    @Test
    public void testRunB_AfterA_Consumer_Asy() {
        //注意： *Async类方法，使用的Executor都是同一个线程池
        //两个异步操作可能使用同一个线程
        print("main thread start");
        CompletableFuture.runAsync(() -> print("task a")).thenAcceptAsync(o -> print("action b"));


        print("main thread end");
    }

    @Test
    public void testRunB_AfterA_Runnable_Asy() {
        //注意： *Async类方法，使用的Executor都是同一个线程池
        //两个异步操作可能使用同一个线程
        print("main thread");
        CompletableFuture.runAsync(() -> print("task a")).thenRunAsync(() -> print("action b"));
    }

    @Test
    public void testRunB_AfterA_Runnable() {
        //注意： 共四次打印操作，第二个跟第一个肯定不一样（异步）, 第三个可能跟第二个一样，也可能跟第一个一样。
        print("main thread");
        CompletableFuture.runAsync(() -> print("task a")).thenRun(() -> print("action b"));
    }

    @Test
    public void testRunB_AfterA_Apply() {
        //注意： 共四次打印操作，第二个跟第一个肯定不一样（异步）, 第三个可能跟第二个一样，也可能跟第一个一样。
        print("main thread");
        CompletableFuture.runAsync(() -> print("task a")).thenApply(o -> {
            print("action b");
            return "result";
        });
    }

    @Test
    public void testRunB_AfterA_Apply_Asy() {
        //注意： 共四次打印操作，第二个跟第一个肯定不一样（异步）, 第三个可能跟第二个一样，也可能跟第一个一样。
        print("main thread");
        CompletableFuture.runAsync(() -> print("task a")).thenApplyAsync(o -> {
            print("action b");
            return "result";
        });
    }

    @Test
    public void testSupplyB_AfterA_Apply() {
        print("main thread");
        CompletableFuture.supplyAsync(() -> {
            print("task a");
            return "task a result";
        }).thenApply(o -> {
            print(o);
            print("action b");
            return "result";
        });
    }


    @Test
    public void testSupplyB_AfterA_Apply_Asy() {
        print("main thread");
        CompletableFuture.supplyAsync(() -> "task a").thenApplyAsync(o -> {
            print(o);
            print("action b");
            return "result";
        });
    }

    @Test
    public void testApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> "hello").thenApply(o -> o+" world");
        print(result.get());
    }

    @Test
    public void testCompletableFutureAnyOf() {
      log.info("begin");
      CompletableFuture<Void> a = CompletableFuture.runAsync(()->{
        try {
          Thread.sleep(3000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.info("我执行完了");
      });
      CompletableFuture<Void> b = CompletableFuture.runAsync(() -> {
        log.info("我也执行完了");
      });
      CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a, b).whenComplete((m,k)->{
        log.info("finish");
//            return "捡田螺的小男孩";
      });
      anyOfFuture.join();
      log.info("end");
    }
  @Test
  public void testCompletableFutureAllOf() {
    log.info("begin");
    CompletableFuture<Void> a = CompletableFuture.runAsync(()->{
      try {
        Thread.sleep(3000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info("我执行完了");
    });
    CompletableFuture<Void> b = CompletableFuture.runAsync(() -> log.info("我也执行完了"));
    CompletableFuture<Void> anyOfFuture = CompletableFuture.allOf(a, b).whenComplete((m, k)->{
      log.info("finish");
//            return "捡田螺的小男孩";
    });
    anyOfFuture.join();// 主线程等待 anyOfFuture执行完
    log.info("end");
  }

    //CountDownLatch编排任务的最好替代工具，减少复杂度
    @Test
    public void testTravelShopDownLoadReportBiz() {

        Executor poolExecutor = getThreadPool("poolExecutor-%d", 5);
        Executor reportDownloadThreadPool = getThreadPool("reportDownloadThreadPool-%d", 25);

        for (int i = 0 ; i < 1 ; ++i) {
            print("task "+ i + " begin");
            List<CompletableFuture> fiveTask = new ArrayList<>();

            AtomicInteger j = new AtomicInteger(0);
            while(j.incrementAndGet() < 5){
                int taskId = j.intValue();
                fiveTask.add(CompletableFuture.supplyAsync(() -> {
                    try {
                        //因为是异步提交，所以所有的j  值都增加到5了。
                        print("table " + taskId +" 开始");
                        TimeUnit.SECONDS.sleep(3);
                        print("table " + taskId +" 结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }, reportDownloadThreadPool));
            }

            CompletableFuture result = CompletableFuture.allOf(fiveTask.toArray(new CompletableFuture[fiveTask.size()])).whenComplete((T,K) -> {
              print("task completed");
            });
            print("print main");
            try {
                result.get(10, TimeUnit.SECONDS);
                print("task finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }


  private static final int TASK_QUEUE_SIZE = 1024;
  private static final int MAXIMUM_POOL_SIZE = 200;
  private static final int CORE_POOL_SIZE = 5;

  /**
   * 阻塞队列--防止任务挤压
   */
  private static BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>(TASK_QUEUE_SIZE);

  private static ExecutorService safetyPoolService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
    30L, TimeUnit.SECONDS,
    taskQueue,
    new ThreadFactoryBuilder().setNameFormat("Safety-verify-Thread-%d").build(),
    (r, executor) -> log.error("the verify executor pool is full!!"));



  private static ScheduledExecutorService schedulerV2 = Executors.newSingleThreadScheduledExecutor(
    new ThreadFactoryBuilder().setNameFormat("Scheduler-Thread-%d").build());

  @Data
  static class FutureTimeoutData {
    long start;
    long timeout;
    CompletableFuture future;
  }
  static List<FutureTimeoutData> completableFutureList = new LinkedList<>();

  public static void timeOutCheck(CompletableFuture future, Integer timeout, TimeUnit unit) {
    FutureTimeoutData futureTimeoutData = new FutureTimeoutData();
    futureTimeoutData.setFuture(future);
    futureTimeoutData.setStart(System.currentTimeMillis());
    futureTimeoutData.setTimeout(unit.toMillis(timeout));
    completableFutureList.add(futureTimeoutData);
  }
  //CountDownLatch编排任务的最好替代工具，减少复杂度
  public static void main(String[] args) {
    testTravelShopDownLoadReportBizTimeout();
  }
  public static void testTravelShopDownLoadReportBizTimeout() {

    List<CompletableFuture<?>> resutList = Optional.ofNullable(Lists.newArrayList("biz1", "biz2")).get()
      .stream()
      .map(infoType -> CompletableFuture.runAsync(() -> {
          try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(infoType);
          }catch (Throwable throwable) {
            log.error("fetch Data error , throwable:{}, infoType:{}",throwable, infoType);
          }

        }, safetyPoolService)
      )
      .parallel()
      .map(future -> CompletableFuture.runAsync(() -> {
        try {
          future.get(20, TimeUnit.MILLISECONDS);
        }catch (Throwable throwable) {
          log.error("timeout");
          if (!future.isDone()) {
            future.cancel(true);
          }
        }
      })).collect(Collectors.toList());


    CompletableFuture<?>[] arry = resutList.toArray(new CompletableFuture<?>[resutList.size()]);
    CompletableFuture<Void> anyOfFuture = CompletableFuture.allOf(arry).whenComplete((m, k)->{
      log.info("finish");
//            return "捡田螺的小男孩";
    });
    anyOfFuture.join();
    log.info("end");

  }


  static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
    Runtime.getRuntime().availableProcessors());


  public static <T> CompletableFuture<T> failAfter(Duration duration){
    /// need a schedular executor
    final CompletableFuture<T> timer = new CompletableFuture<>();
    scheduler.schedule(()->{
      TimeoutException timeoutException = new TimeoutException("time out after "+ duration.getSeconds());
      return timer.completeExceptionally(timeoutException);
    },duration.toMillis(), TimeUnit.MILLISECONDS);
    return timer;
  }

  public static <T> CompletableFuture<T> within(CompletableFuture<T> taskFuture, Duration duration){
    CompletableFuture<T> timeoutWatcher = failAfter(duration);
    return taskFuture.applyToEither(timeoutWatcher, Function.identity());
  }

  private Executor getThreadPool(String nameFormat, int coreSize) {

    ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
    threadFactoryBuilder.setNameFormat(nameFormat);
    ThreadFactory factory = threadFactoryBuilder.build();
    Executor poolExecutor = new ThreadPoolExecutor(coreSize-1, coreSize-1
            , 0, TimeUnit.SECONDS, new SynchronousQueue<>()
            , factory
            , new ThreadPoolExecutor.CallerRunsPolicy()
    );

    return poolExecutor;
  }

}
