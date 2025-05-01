package com.jackdking.service;

import com.jackdking.model.TransInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class TransInfoServiceTest extends BaseTest {

  @Autowired
  TransInfoService transInfoService;

  @Test
  void queryMapperRecordWriteMasterReadMonotonicSlave() {

    TransInfo transInfo = new TransInfo();
    transInfo.setOrderid(111111111L);
    transInfo.setUserId("22222222");

    transInfoService.createOrder(transInfo);
  }
}
