package com.jackdking.sharding.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RWSeparationStrategyType {
    /**
     * 只在主库上进行读写
     */
    RW_SEPARATION_ONLY_MASTER,
    /**
     * 主库上写， 只在从库上读
     */
    RW_SEPARATION_WRITE_MASTER_READ_SLAVE,
    /**
     * 主库上写， 主从库上读
     */
    RW_SEPARATION_WRITE_MASTER_READ_MASTER_SLAVE,
    /**
     * 主库上写， 只在同一个从库上读
     */
    RW_SEPARATION_WRITE_MASTER_READ_MONOTONIC_SLAVE,;

}
