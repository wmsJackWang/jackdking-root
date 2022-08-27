package zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ZkClientTest
 * @Description TODO
 * @Author jackdking
 * @Date 11/08/2022 10:11 上午
 * @Version 2.0
 **/
public class ZkClientTest {

    private CuratorFramework client = null;

    @Before
    public void initClient(){
        ZkClient zkClient = new ZkClient();
        zkClient.init();
        client = zkClient.getClient();
    }


    @Test
    public void testSyncOp() throws Exception {
        String path = "/one";
        byte[] data = "testSyncOp".getBytes();
        client.create().withMode(CreateMode.PERSISTENT).forPath(path,data);

        byte[] actualData = client.getData().forPath(path);
        System.out.println(new String(actualData));
//        client.delete().forPath(path);
        client.close();
    }


    @Test
    public void testChaildernPath() throws Exception {
        String path = "/one/1";
        byte[] data = "testSyncOp".getBytes();
        client.create().withMode(CreateMode.PERSISTENT).forPath(path,data);

        byte[] actualData = client.getData().forPath(path);
        System.out.println(new String(actualData));
//        client.delete().forPath(path);
        client.close();
    }

    @Test
    public void testAsyncOp() throws Exception {
        String path = "/two";
        final byte[] data = "testAsyncOp".getBytes();
        final CountDownLatch latch = new CountDownLatch(1);

        client.getCuratorListenable()
                .addListener((CuratorFramework c, CuratorEvent event) -> {
                    switch (event.getType()) {
                        case CREATE:
                            System.out.printf("znode '%s' created\n", event.getPath());
                            c.getData().inBackground().forPath(event.getPath());
                            break;
                        case GET_DATA:
                            System.out.printf("got the data of znode '%s'\n", event.getPath());
                            System.out.println(new String(event.getData()));
                            c.delete().inBackground().forPath(path);
                            break;
                        case DELETE:
                            System.out.printf("znode '%s' deleted.\n", event.getPath());
                            latch.countDown();
                            break;
                    }
                });

        client.create().withMode(CreateMode.PERSISTENT).inBackground().forPath(path, data);

        latch.await();

        client.close();
    }

    @Test
    public void testWatch() throws Exception {
        String path = "/three";
        byte[] data = {'3'};
        byte[] newData = {'4'};
        CountDownLatch latch = new CountDownLatch(1);

        client.getCuratorListenable()
                .addListener(((CuratorFramework c, CuratorEvent event ) -> {
                    switch (event.getType()) {
                        case WATCHED:
                            WatchedEvent we = event.getWatchedEvent();
                            System.out.println("watched event:" + we);
                            if (we.getType() == Watcher.Event.EventType.NodeDataChanged
                                    && we.getPath().equals(path)) {

                                System.out.println("got the event for the triggered watch");
                                byte[] actualData = c.getData().forPath(path);
                                System.out.println(new String(actualData));
                            }
                            latch.countDown();
                            break;
                    }
                }));
        client.create().withMode(CreateMode.PERSISTENT).forPath(path, data);
        byte[] actualData = client.getData().watched().forPath(path);
        System.out.println(new String(actualData));

        client.setData().forPath(path, newData);
        latch.await();

        client.delete().forPath(path);
    }

    @Test
    public void testCallbackAndWatch() throws Exception {
        String path = "/four";
        byte[] data = {'4'};
        byte[] newData = {'5'};
        CountDownLatch latch = new CountDownLatch(3);

        client.getCuratorListenable()
                .addListener(((CuratorFramework c, CuratorEvent event ) -> {
                    switch (event.getType()) {
                        case CREATE:
                            System.out.printf("znode '%s' created\n", event.getPath());
                            System.out.println(new String(client.getData().watched().forPath(path)));
                            client.setData().forPath(path, newData);
                            latch.countDown();
                            break;
                        case WATCHED:
                            WatchedEvent we = event.getWatchedEvent();
                            System.out.println("watched event:" + we);
                            if (we.getType() == Watcher.Event.EventType.NodeDataChanged
                                    && we.getPath().equals(path)) {

                                System.out.println("got the event for the triggered watch");
                                byte[] actualData = c.getData().forPath(path);
                                System.out.println(new String(actualData));

                            }
                            client.getData().watched().forPath(path);
                            latch.countDown();
                            break;
                    }
                }));
        client.create().withMode(CreateMode.PERSISTENT).inBackground().forPath(path, data);

        latch.await();


        client.setData().forPath(path, "hello java".getBytes());
        TimeUnit.SECONDS.sleep(20);

        client.delete().forPath(path);
    }

}
