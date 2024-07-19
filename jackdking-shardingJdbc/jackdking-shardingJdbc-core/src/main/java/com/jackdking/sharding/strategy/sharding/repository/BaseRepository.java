package com.jackdking.sharding.strategy.sharding.repository;

import com.github.pagehelper.PageInfo;
import com.jackdking.sharding.strategy.sharding.BaseRequest;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BaseRepository
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/17 00:07
 * @Version 2.0
 **/
public interface BaseRepository<R, Q extends BaseRequest> {

    /**查询所有数据 **/
    List<R> selectList(Q q);

    /**统计数据总量 **/
    int count(Q q);

    /**分页查询所有数据 **/
    PageInfo<R> selectPageList(Q q);

    /**查询一条记录 **/
    R selectOne(Q q);

    /**更新数据 **/
    int update(R r);

    /**插入一条记录 **/
    int insert(R r);
}
