package com.jackdking.param;

import com.jackdking.sharding.strategy.sharding.BaseRequest;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class TransInfoRequestParam extends BaseRequest {

  private Long id;

  public String userId;
  public Long orderid;
  public Timestamp ordertime;

}
