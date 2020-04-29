package org.jackdking.shardjdbcyaml.mapper;

import org.jackdking.shardjdbcyaml.bean.TransInfo;

public interface TransInfoMapper {

	public int save(TransInfo info);
	
	public TransInfo get(int id);
	
}
