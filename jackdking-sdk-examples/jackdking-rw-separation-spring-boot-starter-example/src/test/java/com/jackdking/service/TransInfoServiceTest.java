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
  void queryServiceDs0Record() {
    TransInfo data = transInfoService.queryDs0Record(575491223087742976l);
    log.info("query data:{}", data);
  }

  @Test
  void queryServiceDs1Record() {
    TransInfo data = transInfoService.queryDs1Record(575491223087742976l);
    log.info("query data:{}", data);
  }


  @Test
  void queryMapperDs0Record() {
    TransInfo data = transInfoService.queryMapperDs0Record(575491223087742976l);
    log.info("query data:{}", data);
  }

  @Test
  void queryMapperRecordOnlyMaster() {
    TransInfo data = transInfoService.queryMapperDs1Record(575491223087742976l);
    log.info("query data:{}", data);
  }

  @Test
  void queryMapperRecordWriteMasterReadSlave() {
    TransInfo info = new TransInfo();
    info.setOrderid(575491223087742976l);
    TransInfo data = transInfoService.queryMapperDs2Record(info, 575491223087742976l);
    log.info("query data:{}", data);
  }

  @Test
  void queryMapperRecordWriteMasterReadMasterSlave() {
    TransInfo info = new TransInfo();
    info.setOrderid(575491223087742976l);
    TransInfo data = transInfoService.queryMapperDs3Record(info, 575491223087742976l);
    log.info("query data:{}", data);
  }

  @Test
  void queryMapperRecordWriteMasterReadMonotonicSlave() {
    TransInfo info = new TransInfo();
    info.setOrderid(575491223087742976l);
    TransInfo data = transInfoService.queryMapperDs4Record(info, 575491223087742976l);
    log.info("query data:{}", data);
  }
}
