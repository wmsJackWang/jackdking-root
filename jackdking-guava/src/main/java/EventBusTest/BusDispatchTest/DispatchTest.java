package EventBusTest.BusDispatchTest;

import EventBusTest.Event;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName DispatchTest
 * @Description TODO
 * @Author jackdking
 * @Date 14/07/2022 9:38 下午
 * @Version 2.0
 **/
public class DispatchTest {


    @Test
    public void testEventBusDispatch() {
        EventBus eventBus = new EventBus();

        Listener listener = new Listener(eventBus);
        Producer producer = new Producer();
        eventBus.register(listener);
        String produce = producer.produce();
        eventBus.post(produce);
    }
}
