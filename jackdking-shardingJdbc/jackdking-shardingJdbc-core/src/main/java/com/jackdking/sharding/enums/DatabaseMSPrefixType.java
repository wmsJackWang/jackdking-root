package com.jackdking.sharding.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseMSPrefixType {
    MASTER("master"), SLAVE("slave");

    private String prefix;
}
