package gossip;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GossipClusterInfo
 * @Description TODO
 * @Author jackdking
 * @Date 26/06/2022 7:21 下午
 * @Version 2.0
 **/
public class GossipClusterInfo {
  private List<Node> NodeList;//使用uid模拟节点



  static class Node{
    String nodeId;
    String port;
    String ip;


    public void setIp(String ip) {
      this.ip = ip;
    }

    public String getIp() {
      return ip;
    }

    public void setNodeId(String nodeId) {
      this.nodeId = nodeId;
    }

    public String getNodeId() {
      return nodeId;
    }

    public void setPort(String port) {
      this.port = port;
    }

    public String getPort() {
      return port;
    }
  }
}
