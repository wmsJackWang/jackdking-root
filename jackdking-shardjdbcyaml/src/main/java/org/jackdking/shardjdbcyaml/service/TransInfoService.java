package org.jackdking.shardjdbcyaml.service;

import org.jackdking.shardjdbcyaml.bean.TransInfo;
import org.jackdking.shardjdbcyaml.mapper.TransInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class TransInfoService {

	@Autowired
	TransInfoMapper infoMapper;
	
	public int save(TransInfo info) {
		return infoMapper.save(info);
	}
	
	public TransInfo get(int id) {
		
		return infoMapper.get(id);
	}
	
}
