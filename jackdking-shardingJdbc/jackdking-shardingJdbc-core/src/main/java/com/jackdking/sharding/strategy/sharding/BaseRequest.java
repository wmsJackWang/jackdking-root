package com.jackdking.sharding.strategy.sharding;

import lombok.Data;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BaseRequest
 * @Description TODO
 * @Author jackdking
 * @Date 2024/7/16 23:48
 * @Version 2.0
 **/
@Data
public class BaseRequest {

    private int pageSize;

    private int pageNum;

    private boolean isPage = false;

    private String orderBy;

    private SortedType sortedBy = SortedType.DESC;

    public enum SortedType{
        DESC,
        ASC
    }
}
