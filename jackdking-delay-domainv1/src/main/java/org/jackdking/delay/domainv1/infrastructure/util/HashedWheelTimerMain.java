package org.jackdking.delay.domainv1.infrastructure.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

public class HashedWheelTimerMain {


    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    public static void main(String[] args) throws Exception {

        AtomicInteger counter = new AtomicInteger();

        ThreadFactory factory = r -> {

            Thread thread = new Thread(r);

            thread.setDaemon(true);

            thread.setName("HashedWheelTimerWorker-" + counter.getAndIncrement());

            return thread;

        };

        // tickDuration - 每tick一次的时间间隔, 每tick一次就会到达下一个槽位

        // unit - tickDuration的时间单位

        // ticksPerWhee - 时间轮中的槽位数

        Timer timer = new HashedWheelTimer(factory, 1, TimeUnit.SECONDS, 60);

        TimerTask timerTask = new DefaultTimerTask("10086");

        timer.newTimeout(timerTask, 5, TimeUnit.SECONDS);

        timerTask = new DefaultTimerTask("10087");

        timer.newTimeout(timerTask, 10, TimeUnit.SECONDS);

        timerTask = new DefaultTimerTask("10088");

        timer.newTimeout(timerTask, 15, TimeUnit.SECONDS);

        Thread.sleep(Integer.MAX_VALUE);

    }


    private static class DefaultTimerTask implements TimerTask {


        private final String orderId;

        private final long timestamp;


        public DefaultTimerTask(String orderId) {

            this.orderId = orderId;

            this.timestamp = System.currentTimeMillis();

        }


        @Override

        public void run(Timeout timeout) throws Exception {

            System.out.println(String.format("任务执行时间:%s,订单创建时间:%s,订单ID:%s",

                    LocalDateTime.now().format(F), LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(F), orderId));

        }

    }
}
