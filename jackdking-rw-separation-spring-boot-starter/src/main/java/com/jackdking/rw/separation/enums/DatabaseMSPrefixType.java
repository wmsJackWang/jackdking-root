package com.jackdking.rw.separation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseMSPrefixType {
  MASTER("master"), SLAVER("slave")
  ;

  private String prefix;
}
