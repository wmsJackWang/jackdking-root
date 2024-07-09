package com.jackdking.dao;


import com.jackdking.model.TransInfo;
import jackdking.multids.annotation.DBType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransInfoMapper {

  public int save(TransInfo info);

  public TransInfo get(@Param("id") Long id);

  @DBType(value = "ds0")
  TransInfo queryDs0(@Param("id") Long id);

  @DBType(value = "ds1")
  TransInfo queryDs1(@Param("id") Long id);

}
