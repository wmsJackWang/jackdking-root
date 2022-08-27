package zk;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.tomcat.util.buf.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ClusterManager
 * @Description TODO
 * @Author jackdking
 * @Date 11/08/2022 11:06 上午
 * @Version 2.0
 **/
@Slf4j
public class ClusterManager implements Runnable{

    Object objLock = new Object();

    String bizWorkSpace = "/jdkElasticTask";

    String groupName = bizWorkSpace +"/clusterTest";

    String leaderPath =  groupName + "/leader";

    String items = leaderPath + "/items";

    String runningTasks =  groupName + "/runningTasks";

    String nodes =  groupName + "/nodes";

    String dispatch = groupName + "/dispatch";

    static AtomicInteger  clusterInstanceBaseId = new AtomicInteger(1);

    Integer clusterInstanceId = null;

    String nodePath = null;

    String dispatchPath = null;

    CountDownLatch latch = null;

    List<String> jobName = ImmutableList.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");

    List<String> runningJobName = new ArrayList<>();

    private CuratorFramework client = null;

    @Override
    public void run() {
        try {
            init();
            registerCluster();
            registerNode();
            listenNodeDispatchInfo();
            leaderSelector();
            latch.await();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //运行节点   监听分配任务的节点, watched模块，将在集群rebalance阶段起作用。
    private void listenNodeDispatchInfo() throws Exception {

        log.info("listenNodeDispatchInfo, dispatchPath: {}", dispatchPath   );
        client.getCuratorListenable()
                .addListener(((CuratorFramework c, CuratorEvent event ) -> {
                    switch (event.getType()) {
                        case CREATE:
                            log.info("znode '%s' created\n", event.getPath());
                            if (dispatchPath.equals(event.getPath())) {
                                //...start run task
                                log.info("watched path: {}, data: {}", dispatchPath, new String(client.getData().watched().forPath(dispatchPath)));
                            }
                            break;
                        case WATCHED:
                            WatchedEvent we = event.getWatchedEvent();
                            log.info("watched event:" + we);
                            if (we.getType() == Watcher.Event.EventType.NodeDataChanged
                                    && we.getPath().equals(dispatchPath)) {

                                log.info("got the event for the triggered watch");
                                byte[] actualData = c.getData().forPath(dispatchPath);
                                log.info("path: {}, actualData: {}",we.getPath(), new String(actualData));
                            }
                            watchedPath(dispatchPath);
//                            latch.countDown();
                            break;
                    }
                }));
        createPathIfNotExist(dispatchPath);
        watchedPath(dispatchPath);
    }

    private void createPathIfNotExist(String path) throws Exception {
        if (!checkExist(path)) {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path, path.getBytes());
        }
    }

    public void watchedPath(String path) throws Exception {
        client.getData().watched().forPath(path);
    }

    public void init(){
        client = ZkClient.builder().setConnectString("localhost:2181").init();
        clusterInstanceId = clusterInstanceBaseId.incrementAndGet();
        nodePath = nodes + "/" + clusterInstanceId;
        dispatchPath = dispatch + "/" + clusterInstanceId;

        latch = new CountDownLatch(3);
    }

    public void registerCluster() throws Exception {
        Stat stat = client.checkExists().forPath(groupName);
        if (Objects.isNull(stat)) {
            client.create().withMode(CreateMode.PERSISTENT).forPath(bizWorkSpace, bizWorkSpace.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(groupName,groupName.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(leaderPath, leaderPath.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(items,items.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(runningTasks,runningTasks.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(nodes,nodes.getBytes());
            client.create().withMode(CreateMode.PERSISTENT).forPath(dispatch, dispatch.getBytes());

        }
    }

    //节点启动，注册集群的节点
    public void registerNode() throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(getNodePath(nodes), nodes.getBytes());
    }

    private String getNodePath(String nodes) {
        return nodePath;
    }

    /*
     * 主节点负责
     *
     */
    public void leaderSelector(){
        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {


            public void takeLeadership(CuratorFramework client) {

                try {
                    while(true) {
                        log.info("I'm leader now. ");
                        watchNodes(nodes);
                        
                        synchronized (objLock) {
                            objLock.wait();
                        }

                    }
                } catch (InterruptedException e) {
                    System.out.println("takeLeadership was interrupted,this may be server shutdown . " + e.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                log.info("stateChanged: {}", newState.toString());
                if (client.getConnectionStateErrorPolicy().isErrorState(newState)) {
                    synchronized (objLock) {
                        objLock.notifyAll();
                    }
                }
            }
        };

        LeaderSelector selector = new LeaderSelector(client, leaderPath, listener);
        selector.autoRequeue();
        selector.start();
    }

    private void reCover(){

    }

    private void reBalanceClusterTask() throws Exception {

        //获取所有的运行节点，重新分配Task
        Object[] nodeList = client.getChildren().forPath(nodes).toArray();
        log.info(Arrays.toString(nodeList));

        Object[] dispatchList = client.getChildren().forPath(dispatch).toArray();
        if (Objects.isNull(dispatchList) || dispatchList.length ==0) {
            log.error("there is no node exist");
        }
        List<String> dispatchPathList = (List<String>) CollectionUtils.arrayToList(nodeList).stream().map(path -> dispatch +"/" + path).collect(Collectors.toList());
        List<List<String>> balanceResult = spliterList(jobName, nodeList.length);
        log.info("balanceResult:{}", JSON.toJSONString(balanceResult));
        int index = 0;
        for (List<String> ele : balanceResult) {
            String data = StringUtils.join(ele, ',');
            String path = dispatchPathList.get(index++);
            if (!checkExist(path)) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
                log.info("create dispatch node :{}, data:{}", path, data);
            } else {
                client.setData().forPath(path, data.getBytes());
                log.info("update dispatch node :{}, data:{}", path, data);
            }
        }


    }

    // 监听集群所有nodes下的所有子节点。
    private void watchNodes(String path) throws Exception {

        TreeCache treeCache = new TreeCache(client, path);

        treeCache.getListenable().addListener((client, event) -> {
            ChildData eventData = event.getData();
            switch (event.getType()) {
                case NODE_ADDED:
                    log.warn(path + "的子节点上线：" + eventData.getPath() + "\t添加数据为：" + new String(eventData.getData()));
                    break;
                case NODE_UPDATED:
                    log.warn(eventData.getPath() + "子节点数据更新\t更新数据为：" + new String(eventData.getData()) + "\t版本为：" + eventData.getStat().getVersion());
                    break;
                case NODE_REMOVED:
                    log.warn(eventData.getPath() + "子节点下线");
                    break;
                default:
                    break;
            }
            reBalanceClusterTask();
        });
        treeCache.start();
    }

    private boolean checkExist(String path) throws Exception {

        Stat stat = client.checkExists().forPath(path);
        if (Objects.isNull(stat)) {
            return false;
        } else {
            return true;
        }
    }

    public static List<List<String>> spliterList(List<String> arr, int group) {
        //要被分数组string[] arr = new string[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
        List<List<String>> data = new ArrayList<List<String>>();
        int count = arr.size() / group;//新数组分多少个元素
        for (int i = 0; i < group; i++)
        {
            List<String> lst = new ArrayList<>();
            for (int j = i * count; j < i * count + count; j++)
            {
                lst.add(arr.get(j));
            }
            if (i == group-1)
            {
                for (int j = i * count+count; j < arr.size(); j++)
                {
                    int listIndex = j - (i * count+count);
                    data.get(listIndex).add(arr.get(j));
                }
            }
            data.add(lst);
        }
        return data;
    }

    public static void main(String[] args) {

        new Thread(new ClusterManager()).start();
//        List<String> arr = ImmutableList.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" );
//        log.info(JSON.toJSONString(spliterList(arr, 4)));
    }

}
