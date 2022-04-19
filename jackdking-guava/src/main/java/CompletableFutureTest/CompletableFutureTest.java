package CompletableFutureTest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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


    //CountDownLatch编排任务的最好替代工具，减少复杂度
    @Test
    public void testTravelShopDownLoadReportBiz() {

        Executor poolExecutor = getThreadPool("poolExecutor-%d", 5);
        Executor reportDownloadThreadPool = getThreadPool("reportDownloadThreadPool-%d", 25);

        for (int i = 0 ; i < 30 ; ++i) {
            print("task "+ i + " begin");
            poolExecutor.execute(() -> {
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


                CompletableFuture result = CompletableFuture.allOf(fiveTask.toArray(new CompletableFuture[fiveTask.size()]));
                try {
                    result.get();
                    print("task finish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }


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
