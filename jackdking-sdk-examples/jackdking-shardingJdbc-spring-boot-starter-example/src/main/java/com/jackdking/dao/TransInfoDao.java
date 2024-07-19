package com.jackdking.dao;


import com.jackdking.model.TransInfo;
import com.jackdking.param.TransInfoRequestParam;
import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.strategy.sharding.dao.BaseShardingDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@ShardingContext(dbGroupKey = "gorup1", logicTableName = {"trans_info"}, shardingProperty = "userId")
public interface TransInfoDao extends BaseShardingDao<TransInfo, TransInfoRequestParam> {

    //其他使用方法，自定义
}
