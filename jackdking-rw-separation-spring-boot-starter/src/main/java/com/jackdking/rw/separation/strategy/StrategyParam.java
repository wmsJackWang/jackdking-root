package com.jackdking.rw.separation.strategy;

import com.jackdking.rw.separation.enums.MethodOperationType;
import lombok.Data;

@Data
public class StrategyParam {

    /**
     * 主库数据源key
     */
    String masterDataSourceKey;

    /**
     * 读写类型
     */
    MethodOperationType operationType;

    /**
     * 单调读hash字段
     */
    String monotonicProperty;

}
