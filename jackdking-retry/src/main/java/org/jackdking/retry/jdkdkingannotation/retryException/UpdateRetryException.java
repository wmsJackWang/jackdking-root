package org.jackdking.retry.jdkdkingannotation.retryException;

public class UpdateRetryException extends Exception{
	
	/**
	 * @author mingshengwang
	 * @date 下午5:20:27
	 * @todo TODO
	 * @descript serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public UpdateRetryException() {
		// TODO Auto-generated constructor stub
		this("系统乐观锁更新失败");
	}
	
	public UpdateRetryException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
}
