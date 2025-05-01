package com.jackdking.sharding.strategy.sharding.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jackdking.sharding.strategy.sharding.BaseRequest;
import com.jackdking.sharding.strategy.sharding.dao.BaseShardingDao;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BaseRepositoryImpl
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/17 00:16
 * @Version 2.0
 **/
public abstract class BaseRepositoryImpl<R, Q extends BaseRequest> implements BaseRepository<R, Q>{

    public abstract <D extends BaseShardingDao<R, Q>> D shardingDao();

    @Override
    public List<R> selectList(Q q) {
        return shardingDao().selectList(q);
    }

    @Override
    public int count(Q q) {
        return shardingDao().count(q);
    }

    @Override
    public PageInfo<R> selectPageList(Q q) {

        q.setOrderBy(q.getOrderBy() + " " + (q.getSortedBy().equals(BaseRequest.SortedType.DESC) ? "DESC" : "ASC"));
        q.setPage(true);
        PageInfo<R> pageInfo = PageHelper.startPage(q.getPageNum(), q.getPageSize())
                .doSelectPageInfo(() -> shardingDao().selectPageList(q));
        return pageInfo;
    }

    @Override
    public R selectOne(Q q) {
        return shardingDao().selectOne(q);
    }

    @Override
    public int update(R r) {
        return shardingDao().update(r);
    }

    @Override
    public int insert(R r) {
        return shardingDao().insert(r);
    }
}
