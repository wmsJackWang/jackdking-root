package org.jackdking.retry.jdkdkingannotation.recover.impl;

import org.jackdking.retry.jdkdkingannotation.recover.Recover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("DefaultRecoverImpl")
public class DefaultRecoverImpl implements Recover{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultRecoverImpl.class);


	@Override
	public String recover(Object obj) {
		// TODO Auto-generated method stub
		log.error("重试失败，未进行任何补全，此为默认补全：打出错误日志");
		return null;
	}

}
