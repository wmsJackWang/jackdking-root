package EventBusTest.BusDispatchTest;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Listener
 * @Description TODO
 * @Author jackdking
 * @Date 14/07/2022 9:37 下午
 * @Version 2.0
 **/
public class Listener {
    private final EventBus eventBus;

    public Listener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void record(String s) {
        eventBus.post(s);
        System.out.println("receive:"+ s);
    }
}
