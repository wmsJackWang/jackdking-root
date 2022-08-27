package zk;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName PathChaildernListener
 * @Description TODO
 * @Author jackdking
 * @Date 11/08/2022 8:30 下午
 * @Version 2.0
 **/


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class PathChildrenListener {



    private static Logger logger = LoggerFactory.getLogger(PathChildrenListener.class);
    private static CountDownLatch watch = new CountDownLatch(1);
    private static final String zkServerIps = "localhost:2181";  //可以写多个地址，逗号分隔
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    // 实例化Curator客户端
    private static CuratorFramework client = CuratorFrameworkFactory.builder()                  // 使用工厂类来建造客户端的实例对象
            .connectString(zkServerIps)                         // 放入zookeeper服务器ip
            .sessionTimeoutMs(15000).retryPolicy(retryPolicy)   // 设定会话时间以及重连策略
            .build();                                           // 建立连接通道

    /**
     * 注册监听
     * TreeCache: 可以将指定的路径节点作为根节点（祖先节点），对其所有的子节点操作进行监听，
     * 呈现树形目录的监听，可以设置监听深度，最大监听深度为 int 类型的最大值。
     */
    private static void zkWatch(String path) throws Exception {
        TreeCache treeCache = new TreeCache(client, path);

        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData eventData = event.getData();
                switch (event.getType()) {
                    case NODE_ADDED:
                        logger.warn(path + "节点添加" + eventData.getPath() + "\t添加数据为：" + new String(eventData.getData()));
                        break;
                    case NODE_UPDATED:
                        logger.warn(eventData.getPath() + "节点数据更新\t更新数据为：" + new String(eventData.getData()) + "\t版本为：" + eventData.getStat().getVersion());
                        break;
                    case NODE_REMOVED:
                        logger.warn(eventData.getPath() + "节点被删除");
                        break;
                    default:
                        break;
                }
            }
        });

        treeCache.start();
        watch.await();  //如果不执行 watch.countDown()，进程会一致阻塞在 watch.await()
    }

    // 获取节点数据
    private static void getZnode(String path) throws Exception {

        byte[] bytes = client.getData().forPath(path);                     // 普通查询
        System.out.println(new String(bytes));
    }

    // 关闭连接
    private static void closeZnode() {
        if (client != null) {
            client.close();
        }
    }


    public static void main(String[] args) throws Exception {
        client.start();         // 启动Curator客户端
        System.out.println(client.getState());    //获取连接状态
        String path = "/one";
        getZnode(path);		   // 获取节点数据
        zkWatch(path);          // 监听这个节点
        closeZnode();           // 关闭Curator客户端
    }

}

