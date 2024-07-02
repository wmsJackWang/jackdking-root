package com.jackdking.rw.separation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DatabaseMSType {
  MASTER("master"), SLAVER("slaver")
  ;

  @Getter
  private String prefix;
}
