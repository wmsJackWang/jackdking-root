package com.jackdking.dao;


import com.jackdking.model.TransInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransInfoMapper {

	public int save(TransInfo info);
	
	public TransInfo get(@Param("id") Long id);
	
}
