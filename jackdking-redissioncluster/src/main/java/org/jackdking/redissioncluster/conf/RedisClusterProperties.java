package org.jackdking.redissioncluster.conf;

//@Data
//@ToString
public class RedisClusterProperties {

    /**
     * 集群状态扫描间隔时间，单位是毫秒
     */
    private int scanInterval;

    /**
     * 集群节点
     */
    private String nodes;

    /**
     * 默认值： SLAVE（只在从服务节点里读取）设置读取操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里读取。
     * MASTER - 只在主服务节点里读取。 MASTER_SLAVE - 在主从服务节点里都可以读取
     */
    private String readMode;
    /**
     * （从节点连接池大小） 默认值：64
     */
    private int slaveConnectionPoolSize;
    /**
     * 主节点连接池大小）默认值：64
     */
    private int masterConnectionPoolSize;

    /**
     * （命令失败重试次数） 默认值：3
     */
    private int retryAttempts;

    /**
     *命令重试发送时间间隔，单位：毫秒 默认值：1500
     */
    private int retryInterval;

    /**
     * 执行失败最大次数默认值：3
     */
    private int failedAttempts;

	public int getScanInterval() {
		return scanInterval;
	}

	public void setScanInterval(int scanInterval) {
		this.scanInterval = scanInterval;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public String getReadMode() {
		return readMode;
	}

	public void setReadMode(String readMode) {
		this.readMode = readMode;
	}

	public int getSlaveConnectionPoolSize() {
		return slaveConnectionPoolSize;
	}

	public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
		this.slaveConnectionPoolSize = slaveConnectionPoolSize;
	}

	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}

	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}

	public int getRetryAttempts() {
		return retryAttempts;
	}

	public void setRetryAttempts(int retryAttempts) {
		this.retryAttempts = retryAttempts;
	}

	public int getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	@Override
	public String toString() {
		return "RedisClusterProperties [scanInterval=" + scanInterval + ", nodes=" + nodes + ", readMode=" + readMode
				+ ", slaveConnectionPoolSize=" + slaveConnectionPoolSize + ", masterConnectionPoolSize="
				+ masterConnectionPoolSize + ", retryAttempts=" + retryAttempts + ", retryInterval=" + retryInterval
				+ ", failedAttempts=" + failedAttempts + "]";
	}
    
    
}

 