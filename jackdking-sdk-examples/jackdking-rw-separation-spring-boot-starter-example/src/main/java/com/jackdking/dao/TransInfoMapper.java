package com.jackdking.dao;


import com.jackdking.model.TransInfo;
import com.jackdking.rw.separation.annotation.RWSeparationDBContext;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransInfoMapper {

	public int save(TransInfo info);

	public TransInfo get(@Param("id") Long id);

	@RWSeparationDBContext(dsKey = "ds3", monotonicPropertyExp = "#ids")
	TransInfo queryDsV1(@Param("id") Long id);

    TransInfo queryDsV2(@Param("id") Long id, @Param("ids") Long ids);

	TransInfo queryDsV3(@Param("info")TransInfo transInfo, @Param("id")Long id);

}
