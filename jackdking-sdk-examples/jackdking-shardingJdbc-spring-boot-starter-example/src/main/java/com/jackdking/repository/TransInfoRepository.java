package com.jackdking.repository;

import com.jackdking.dao.TransInfoDao;
import com.jackdking.model.TransInfo;
import com.jackdking.param.TransInfoRequestParam;
import com.jackdking.sharding.strategy.sharding.repository.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TransInfoRepository
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/17 00:58
 * @Version 2.0
 **/
@Repository
public class TransInfoRepository extends BaseRepositoryImpl<TransInfo, TransInfoRequestParam> {

    @Autowired
    private TransInfoDao transInfoDao;

    @Override
    public TransInfoDao shardingDao() {
        return transInfoDao;
    }
}
