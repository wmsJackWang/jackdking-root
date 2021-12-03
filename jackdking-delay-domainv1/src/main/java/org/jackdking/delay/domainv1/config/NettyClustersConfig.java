package org.jackdking.delay.domainv1.config;

public class NettyClustersConfig {

    private int clientSocketSndBufSize = JdkMQSystemConfig.SocketSndbufSize;
    private int clientSocketRcvBufSize = JdkMQSystemConfig.SocketRcvbufSize;
    private static int workerThreads = Runtime.getRuntime().availableProcessors() * 2;

    public static int getWorkerThreads() {
        return workerThreads;
    }

    public static void setWorkerThreads(int workers) {
        workerThreads = workers;
    }

    public int getClientSocketSndBufSize() {
        return clientSocketSndBufSize;
    }

    public void setClientSocketSndBufSize(int clientSocketSndBufSize) {
        this.clientSocketSndBufSize = clientSocketSndBufSize;
    }

    public int getClientSocketRcvBufSize() {
        return clientSocketRcvBufSize;
    }

    public void setClientSocketRcvBufSize(int clientSocketRcvBufSize) {
        this.clientSocketRcvBufSize = clientSocketRcvBufSize;
    }
}
