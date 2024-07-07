package com.jackdking.dao;


import com.jackdking.model.TransInfo;
import com.jackdking.rw.separation.annotation.RWSeparationDBContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransInfoMapper {

	public int save(TransInfo info);

	public TransInfo get(@Param("id") Long id);

	@RWSeparationDBContext(dsKey = "ds0")
	TransInfo queryDsV1(@Param("id") Long id);

    @RWSeparationDBContext(dsKey = "ds3")
    TransInfo queryDsV2(@Param("id") Long id, @Param("ids") Long ids);

	@RWSeparationDBContext(dsKey = "ds3", monotonicPropertyExp = "#info.orderid")
	TransInfo queryDsV3(@Param("info")TransInfo info, @Param("id")Long id);

}
