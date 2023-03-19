package gossip;

import heartbeat.GossipClient;
import heartbeat.GossipServer;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GossipClusterManager
 * @Description TODO
 * @Author jackdking
 * @Date 20/11/2022 8:21 下午
 * @Version 2.0
 **/
public class GossipClusterManager {

  GossipServer gossipServer;
  GossipClient gossipClient;
  GossipClusterInfo clientInfo;

  //manager 启动
  public void start(){

    //连接其他node，告知接入gossip集群
    //接收其他集群发送的集群信息包，更新集群信息

    //心跳检测，检测节点的上线和下线，执行gossip集群节点下线和上线流程。

    //其他在线节点接听到接入信息，则发送询问包到各节点，如果超过1/2节点数量同意，则所有节点集群增加该节点。

    //监听到节点断开，则发送询问信息包到各节点，如果超过1/2节点数量同意，则所有节点断开该节点。

  }

  public void setClientInfo(GossipClusterInfo clientInfo) {
    this.clientInfo = clientInfo;
  }

  public GossipClient getGossipClient() {
    return gossipClient;
  }

  public void setGossipClient(GossipClient gossipClient) {
    this.gossipClient = gossipClient;
  }

  public GossipClusterInfo getClientInfo() {
    return clientInfo;
  }

  public void setGossipServer(GossipServer gossipServer) {
    this.gossipServer = gossipServer;
  }

  public GossipServer getGossipServer() {
    return gossipServer;
  }
}
