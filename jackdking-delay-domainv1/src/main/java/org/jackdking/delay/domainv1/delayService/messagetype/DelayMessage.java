package org.jackdking.delay.domainv1.delayService.messagetype;

import java.io.Serializable;

public abstract class DelayMessage<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getMessageJson();

}
