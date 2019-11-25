package org.jackdking.redissioncluster.conf;

//import lombok.Data;
//import lombok.ToString;

/**
 * @author Abbot
 * @des redis 池配置
 * @date 2018/10/18 10:43
 **/
/*@Data
@ToString*/
public class RedisPoolProperties {

    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "RedisPoolProperties [maxIdle=" + maxIdle + ", minIdle=" + minIdle + ", maxActive=" + maxActive
				+ ", maxWait=" + maxWait + ", connTimeout=" + connTimeout + ", soTimeout=" + soTimeout + ", size="
				+ size + "]";
	}
    
    
    

}