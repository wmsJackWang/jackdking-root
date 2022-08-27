package zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ZkClient
 * @Description TODO
 * @Author jackdking
 * @Date 11/08/2022 10:11 上午
 * @Version 2.0
 **/
public class ZkClient {

    String connectString = "localhost:2181";
    private CuratorFramework client = null;        //doSomething to zookeeper

    public static ZkClient builder(){
        return new ZkClient();
    }

    public CuratorFramework init(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // Fluent 风格
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryPolicy)
                .build();
        //启动 client
        client.start();
        return client;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public ZkClient setConnectString(String connectString) {
        this.connectString = connectString;
        return ZkClient.this;
    }
}
