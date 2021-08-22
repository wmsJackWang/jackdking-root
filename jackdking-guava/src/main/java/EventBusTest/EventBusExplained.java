package EventBusTest;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//事件总线
@Slf4j
public class EventBusExplained {

    @Test
    public void testEventBus() {
        /**
         * Guava事件总线 , 是同步执行的， 事件总线其实  就是生产者消费者编程模型，使用了观察者模式
         */
        EventBus eventBus = new EventBus("test");
        EventBusChangeRecorder changeRecorder = new EventBusChangeRecorder();

        // 把事件监听者注册到事件生产者
        eventBus.register(changeRecorder);
        // 向监听者分发事件
        eventBus.post(new Event("中奖啦！"));
        eventBus.post(new Integer(300));
        eventBus.post(new Long(800));
    }

    @Test
    public void testAsynEventBus() {

        AsyncEventBus asyncEventBus = new AsyncEventBus("testAsyncEventBus", MoreExecutors.getExitingExecutorService((ThreadPoolExecutor) Executors.newFixedThreadPool(5)));

        EventBusChangeRecorder changeRecorder = new EventBusChangeRecorder();
        asyncEventBus.register(changeRecorder);

        asyncEventBus.post(Event.builder().msg("异步eventbus1").build());
        asyncEventBus.post(Event.builder().msg("异步eventbus2").build());
        asyncEventBus.post(Event.builder().msg("异步eventbus3").build());
        asyncEventBus.post(Event.builder().msg("异步eventbus4").build());
        asyncEventBus.post(Event.builder().msg("异步eventbus5").build());

    }
}